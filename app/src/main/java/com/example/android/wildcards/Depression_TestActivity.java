package com.example.android.wildcards;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.attr.y;

public class Depression_TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depression__test);

        final RadioGroup rg1=(RadioGroup)findViewById(R.id.rg1);
        final RadioGroup rg2=(RadioGroup)findViewById(R.id.rg2);
        final RadioGroup rg3=(RadioGroup)findViewById(R.id.rg3);
        final RadioGroup rg4=(RadioGroup)findViewById(R.id.rg4);
        final RadioGroup rg5=(RadioGroup)findViewById(R.id.rg5);
        final RadioGroup rg6=(RadioGroup)findViewById(R.id.rg6);
        final RadioGroup rg7=(RadioGroup)findViewById(R.id.rg7);
        Button dep_button=(Button)findViewById(R.id.dep_button);

        dep_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jObj = new JSONObject();
                int length = ((RadioButton)findViewById(rg1.getCheckedRadioButtonId())).toString().length();
                String selectedText1 = ((RadioButton)findViewById(rg1.getCheckedRadioButtonId())).toString();
                String selectedText2 = ((RadioButton)findViewById(rg2.getCheckedRadioButtonId())).toString();
                String selectedText3 = ((RadioButton)findViewById(rg3.getCheckedRadioButtonId())).toString();
                String selectedText4 = ((RadioButton)findViewById(rg4.getCheckedRadioButtonId())).toString();
                String selectedText5 = ((RadioButton)findViewById(rg5.getCheckedRadioButtonId())).toString();
                String selectedText6 = ((RadioButton)findViewById(rg6.getCheckedRadioButtonId())).toString();
                String selectedText7 = ((RadioButton)findViewById(rg7.getCheckedRadioButtonId())).toString();
                try {

                    jObj.put("1", selectedText1.charAt(length-1));
                    jObj.put("2",  selectedText2.charAt(length-1));
                    jObj.put("3",  selectedText3.charAt(length-1));
                    jObj.put("4",  selectedText4.charAt(length-1));
                    jObj.put("5",  selectedText5.charAt(length-1));
                    jObj.put("6",  selectedText6.charAt(length-1));
                    jObj.put("7",  selectedText7.charAt(length-1));

                    new Depression_TestActivity.SendDeviceDetails().execute("http://52.88.194.67:8080/IOTProjectServer/registerDevice", jObj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent i=new Intent(Depression_TestActivity.this,Schedule_TestActivity.class);
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
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }
}
