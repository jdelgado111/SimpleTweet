package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    public static final int MAX_TWEET_LENGTH = 140;

    private EditText etCompose;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Display icon in the timeline_toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twit_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        client = TwitterApp.getRestClient(this);
        etCompose = findViewById(R.id.etCompose);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.miTweet) {
            String tweetContent = etCompose.getText().toString();
            // TODO: Error-handling
            if (tweetContent.isEmpty()) {
                Toast.makeText(ComposeActivity.this, "Your Tweet is empty!", Toast.LENGTH_LONG).show();
                return false;
            }
            if (tweetContent.length() > MAX_TWEET_LENGTH) {
                Toast.makeText(ComposeActivity.this, "Your Tweet is too long!", Toast.LENGTH_LONG).show();
                return false;
            }

            //Toast.makeText(ComposeActivity.this, tweetContent, Toast.LENGTH_LONG).show();
            //make API call to Twitter to publish the content in edit text
            client.composeTweet(tweetContent, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //super.onSuccess(statusCode, headers, response);
                    Log.d("TwitterClient", "Successfully posted tweet " + response.toString());

                    //return Tweet
                    try {
                        Tweet tweet = Tweet.fromJson(response);

                        Intent data = new Intent();
                        data.putExtra("tweet", Parcels.wrap(tweet));

                        //set result code and bundle data for response
                        setResult(RESULT_OK, data);

                        //close the activity, pass data to parent
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    //super.onFailure(statusCode, headers, responseString, throwable);
                    Log.e("TwitterClient", "Failed to post tweet " + responseString);
                }
            }); //end client

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
