package com.amol.plusmini;


import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.SignInButton;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

//    private static

    private static final int SOME_REQ_CODE = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* final ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);*/
        SignInButton signinbutton = (SignInButton) findViewById(R.id.sign_in_button);
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
                startActivityForResult(intent, SOME_REQ_CODE);
            }
        });
    }

    protected String accountName = "";
    protected String token = "";

    @Override
    protected void onActivityResult(final int REQ_CODE, final int RESULT_CODE, final Intent data) {
        if (REQ_CODE == SOME_REQ_CODE && RESULT_CODE == RESULT_OK) {
            accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            Log.i("Account Name: ", accountName);
            new AuthTask().execute();
        }
    }

    private class AuthTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String scopes = "oauth2:" + "https://www.googleapis.com/auth/plus.me " +
                    "https://www.googleapis.com/auth/plus.circles.read";
            final int REQUEST_AUTHORIZATION = 2;

            try {
                token = GoogleAuthUtil.getToken(MainActivity.this,
                        accountName, scopes);
            } catch (UserRecoverableAuthException e) {
                startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
            } catch (GoogleAuthException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return token;
        }

        @Override
        protected void onPostExecute(String token) {
            System.out.print("-----------------------------" + token);
            Intent i = new Intent(MainActivity.this, ActionBarMain.class);
            i.putExtra("token", token);
            startActivity(i);
        }
    }

    ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
