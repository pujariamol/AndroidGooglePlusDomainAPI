package com.amol.plusmini;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.api.services.plusDomains.model.Person;

import java.io.IOException;
import java.io.InputStream;

public class ProfileFragment extends Fragment {
    private ImageView displayPicture = null;
    private Person profile = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profile = ShareObject.getSelfProfile();

        View view = inflater.inflate(R.layout.profile, null);

        displayPicture = (ImageView) view.findViewById(R.id.display_picture);
        try {
            TextView aboutMe = (TextView) view.findViewById(R.id.about_me);
            String aboutMeVal = "N/A";

            if (null != profile.getAboutMe()) {
                aboutMeVal = profile.getAboutMe();
            }

            aboutMe.setText(aboutMeVal);

            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText((profile.getDisplayName() == null) ? "N/A" : profile.getDisplayName());

            TextView gender = (TextView) view.findViewById(R.id.about_me);
            aboutMe.setText((profile.getGender() == null) ? "N/A" : profile.getGender());

            TextView birthday = (TextView) view.findViewById(R.id.about_me);
            aboutMe.setText((profile.getBirthday() == null) ? "N/A" : profile.getBirthday());
            new ParseImage().execute();

            view.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        } catch (Exception e) {
            Log.i("Info","---------------------------------- ");
            e.printStackTrace();
        }
        return view;
    }

    private class ParseImage extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap dp;

        @Override
        protected Bitmap doInBackground(String... params) {
            String dpURL = profile.getImage().getUrl().substring(0,
                    profile.getImage().getUrl().length() - 6);
            InputStream in = null;
            try {
                in = new java.net.URL(dpURL).openStream();
                dp = BitmapFactory.decodeStream(in);
                dp = Bitmap.createScaledBitmap(dp, 300, 300, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return dp;
        }

        @Override
        protected void onPostExecute(Bitmap dp) {
            super.onPostExecute(dp);
            displayPicture.setImageBitmap(dp);

        }


    }
}
