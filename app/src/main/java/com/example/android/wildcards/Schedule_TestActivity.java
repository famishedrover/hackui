package com.example.android.wildcards;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.android.wildcards.jsonparsing.LOG_TAG;

public class Schedule_TestActivity extends AppCompatActivity {

    public static String msleephrs="";
    public static String mworkhrs="";
    public static String mfreetime="";
    public static String mholperyear="";
    public static String old_sscore="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_test);

        final EditText edittext1=(EditText)findViewById(R.id.edit_view1);
        final EditText edittext2=(EditText)findViewById(R.id.edit_view2);
        final EditText edittext3=(EditText)findViewById(R.id.edit_view3);
        final EditText edittext4=(EditText)findViewById(R.id.edit_view4);

        Button scheduleButton=(Button)findViewById(R.id.schedule_button);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray jArr = new JSONArray();
                JSONObject jObj = new JSONObject();
                try {

                    jObj.put("sleep_hours", edittext1.getText().toString());
                    jObj.put("work_hours",  edittext2.getText().toString());
                    jObj.put("freetime",  edittext3.getText().toString());
                    jObj.put("holperyear",  edittext4.getText().toString());

                    jArr.put(jObj);
                    new Schedule_TestActivity.SendDeviceDetails().execute("https://dtuwildcards.herokuapp.com/app/schedule/", jObj.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent i=new Intent(Schedule_TestActivity.this,ImprovementActivity.class);
                startActivity(i);
            }
        });

    }

    private class SendDeviceDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("PostData=" + params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (TextUtils.isEmpty(result)) {
                Log.v(LOG_TAG, "Problem parsing the earthquake JSON results");
            }

            try {
                JSONObject baseJsonResponse = new JSONObject(result);
                msleephrs = baseJsonResponse.getString("sleep_hours");
                mworkhrs = baseJsonResponse.getString("work_hours");
                mfreetime=   baseJsonResponse.getString("freetime");
                mholperyear = baseJsonResponse.getString("holperyear");
                old_sscore= baseJsonResponse.getString("old_sscore");
                // Create a new {@link Event} object
                //return new Event(sleep_hrs,work_hrs,free_time,hol_peryear);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
            }
            Log.e("GOYAL", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }
}
