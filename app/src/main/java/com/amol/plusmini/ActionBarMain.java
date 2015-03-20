package com.amol.plusmini;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.plusDomains.PlusDomains;
import com.google.api.services.plusDomains.model.Circle;
import com.google.api.services.plusDomains.model.CircleFeed;
import com.google.api.services.plusDomains.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActionBarMain extends Activity implements TabListener {
    private static final String PROFILE_TAB = "Profile";
    private static final String CIRCLE_TAB = "Circle";
    private static final String TAG = "ActionBarMain";
	RelativeLayout rl;

	CircleFragment mCircleFragment;
	FragmentTransaction mFragmentTransaction = null;
	ProfileFragment mProfileFragment;
    private String token = "";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        token = getIntent().getStringExtra("token");
        ShareObject.setToken(token);
        setTheme(android.R.style.Theme_Holo_Light);
		setContentView(R.layout.activity_action_bar_main);
		try {
			rl = (RelativeLayout) findViewById(R.id.mainLayout);
			mFragmentTransaction = getFragmentManager().beginTransaction();
			ActionBar bar = getActionBar();

			bar.addTab(bar.newTab().setText(PROFILE_TAB).setTabListener(this));
			bar.addTab(bar.newTab().setText(CIRCLE_TAB).setTabListener(this));

			bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_USE_LOGO);
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			bar.setDisplayShowHomeEnabled(true);
			bar.setDisplayShowTitleEnabled(false);
			bar.show();

		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
        try {
            rl.removeAllViews();
        } catch (Exception e) {
        }
		if (tab.getText().equals(CIRCLE_TAB)) {
            new CircleTask().execute();


		} else if (tab.getText().equals(PROFILE_TAB)) {
            new ProfileTask().execute();
		}

	}

    private class ProfileTask extends AsyncTask<String, Void, Person> {
        private Person selfProfile = null;

        @Override
        protected Person doInBackground(String... params) {
            GoogleCredential googleCredential = new GoogleCredential().setAccessToken(token);
            PlusDomains plusDomains = new PlusDomains.Builder(new NetHttpTransport(), new JacksonFactory(),
                    googleCredential).setApplicationName("PlusMini").build();
            try {
                selfProfile = plusDomains.people().get("me").execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return selfProfile;
        }

        @Override
        protected void onPostExecute(Person selfProfile) {
            super.onPostExecute(selfProfile);
            mProfileFragment = new ProfileFragment();
            ShareObject.setSelfProfile(selfProfile);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction = getFragmentManager().beginTransaction();
            mFragmentTransaction.add(rl.getId(), mProfileFragment);
            mFragmentTransaction.commit();
        }
    }

    private class CircleTask extends AsyncTask<String, Void, List<Circle>> {
        private List<Circle> circles = new ArrayList<Circle>();

        @Override
        protected List<Circle> doInBackground(String... params) {

            GoogleCredential googleCredential = new GoogleCredential().setAccessToken(token);
            PlusDomains plusDomains = new PlusDomains.Builder(new NetHttpTransport(), new JacksonFactory(),
                    googleCredential).setApplicationName("PlusMini").build();
            try {
                PlusDomains.Circles.List listCircles = plusDomains.circles().list("me");
                listCircles.setMaxResults(5L);
                CircleFeed circleFeed = listCircles.execute();
                circles = circleFeed.getItems();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return circles;
        }

        @Override
        protected void onPostExecute(List<Circle> listOfCircles) {
            super.onPostExecute(listOfCircles);
            ShareObject.setCircleList(listOfCircles);
            mCircleFragment = new CircleFragment();

            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction = getFragmentManager().beginTransaction();
            mFragmentTransaction.add(rl.getId(), mCircleFragment);
            mFragmentTransaction.commit();
        }
    }

    @Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

}
