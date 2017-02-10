package com.example.android.wildcards;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Schedule_TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_test);

        final EditText edittext1=(EditText)findViewById(R.id.edit_view1);
        final EditText edittext2=(EditText)findViewById(R.id.edit_view2);
        final EditText edittext3=(EditText)findViewById(R.id.edit_view3);
        final EditText edittext4=(EditText)findViewById(R.id.edit_view4);
        final EditText edittext5=(EditText)findViewById(R.id.edit_view5);
        final EditText edittext6=(EditText)findViewById(R.id.edit_view6);

        Button scheduleButton=(Button)findViewById(R.id.schedule_button);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray jArr = new JSONArray();
                JSONObject jObj = new JSONObject();
                try {

                    jObj.put("Working Hours", edittext1.getText().toString());
                    jObj.put("Working Hours",  edittext2.getText().toString());
                    jObj.put("Working Hours",  edittext3.getText().toString());
                    jObj.put("Working Hours",  edittext4.getText().toString());
                    jObj.put("Working Hours",  edittext5.getText().toString());
                    jObj.put("Working Hours",  edittext6.getText().toString());

                    jArr.put(jObj);
                    new Schedule_TestActivity.SendDeviceDetails().execute("http://52.88.194.67:8080/IOTProjectServer/registerDevice", jObj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }
}
