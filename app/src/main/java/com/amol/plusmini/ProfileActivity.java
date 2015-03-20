package com.amol.plusmini;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.services.plusDomains.model.Person;

import java.io.IOException;
import java.io.InputStream;


public class ProfileActivity extends Activity {

    private Person person = null;
    private ImageView displayPicture = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        person = ShareObject.getSelfProfile();

        displayPicture = (ImageView) findViewById(R.id.display_picture);


        TextView aboutMe = (TextView) findViewById(R.id.about_me);
        aboutMe.setText((person.getAboutMe() == null) ? "N/A" : person.getAboutMe());

        TextView name = (TextView) findViewById(R.id.name);
        name.setText((person.getDisplayName() == null) ? "N/A" : person.getDisplayName());
        Log.i("Info"," "+person.getGender() + " " +person.getAboutMe() + " " +  person.getBirthday());
        TextView gender = (TextView) findViewById(R.id.about_me);
        aboutMe.setText((person.getGender() == null) ? "N/A" : person.getGender());

        TextView birthday = (TextView) findViewById(R.id.about_me);
        aboutMe.setText((person.getBirthday() == null) ? "N/A" : person.getBirthday());
        new ParseImage().execute();
    }

    private class ParseImage extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap dp;

        @Override
        protected Bitmap doInBackground(String... params) {
            String dpURL = person.getImage().getUrl().substring(0,
                    person.getImage().getUrl().length() - 6);
            InputStream in = null;
            try {
                in = new java.net.URL(dpURL).openStream();

                dp = BitmapFactory.decodeStream(in);
                dp = Bitmap.createScaledBitmap(dp, 400, 400, true);
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
