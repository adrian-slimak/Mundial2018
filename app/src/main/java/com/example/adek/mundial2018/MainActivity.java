package com.example.adek.mundial2018;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    TextView mTextView;
    Context mContext;
    String mJSONURLString = "http://livescore-api.com/api-client/scores/live.json?key=he0S3YVbJApEcWre&secret=rAOqB5MzLfsOmPwD3ww7vsie2ITeFls6";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView);
        mContext = getApplicationContext();
    }

    public void OnRefreshClick(View v)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try{
                            // Get the JSON array
                            JSONArray array = response.getJSONObject("data").getJSONArray("match");

                            // Loop through the array elements
                            for(int i=0;i<array.length();i++){
                                // Get current json object
                                JSONObject match = array.getJSONObject(i);

                                // Get the current student (json object) data
                                String home = match.getString("home_name");
                                String away = match.getString("away_name");
                                String time = match.getString("time");
                                String score = match.getString("score");
                                String status = match.getString("status");

                                // Display the formatted json data in text view
                                mTextView.append(home +" - " + away +"\nscore : " + score +"\ntime : " + time + "\nstatus : "+status);
                                mTextView.append("\n\n");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
