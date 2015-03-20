package com.amol.plusmini;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plusDomains.PlusDomains;
import com.google.api.services.plusDomains.model.Circle;
import com.google.api.services.plusDomains.model.PeopleFeed;
import com.google.api.services.plusDomains.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CircleFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "CircleFragment";
	private List<Circle> circleList = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

        circleList = ShareObject.getCircleList();

		ListView listView = new ListView(getActivity());
		ArrayAdapter<String> array = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1);
		for (Circle circle : circleList) {
            array.add(circle.getDisplayName());
        }
		listView.setAdapter(array);
        listView.setOnItemClickListener(this);
		return listView;
	}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG, ""+adapterView.getSelectedItem());
        Log.d(TAG, "List item clicked --> " + adapterView.getItemAtPosition(i));
        for(Circle circle : circleList){
            if(circle.getDisplayName().equalsIgnoreCase(adapterView.getItemAtPosition(i).toString())){
                String circleId = circle.getId();
                new CirclePeopleTask().execute(circleId);
                break;
            }

        }

//        Log.d(TAG, "List item clicked --> " + adapterView.getItemAtPosition(i));
//        Intent intent = new Intent(getActivity(), PersonsListActivity.class);
//        startActivity(intent);
    }


    private class CirclePeopleTask extends AsyncTask<String, Void, List<Person>> {

        @Override
        protected List<Person> doInBackground(String... params) {
            Log.i(TAG,"--------------" + params[0]);
            GoogleCredential googleCredential = new GoogleCredential().setAccessToken(ShareObject.getToken());
            PlusDomains plusDomains = new PlusDomains.Builder(new NetHttpTransport(), new JacksonFactory(),
                    googleCredential).setApplicationName("PlusMini").build();
            List<Person> circlePeople = new ArrayList<Person>();

            try {
                PeopleFeed peopleList = plusDomains.people().listByCircle(params[0]).execute();
                for (Person person : peopleList.getItems()) {
                    circlePeople.add(person);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return circlePeople;
        }

        @Override
        protected void onPostExecute(List<Person> circlePeople) {
            super.onPostExecute(circlePeople);
            ShareObject.setCirclePeople(circlePeople);
            Intent intent = new Intent(getActivity(), PersonsListActivity.class);
            startActivity(intent);
        }
    }


}
