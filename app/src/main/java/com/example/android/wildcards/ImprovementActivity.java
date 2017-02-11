package com.example.android.wildcards;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.android.wildcards.Schedule_TestActivity.mfreetime;
import static com.example.android.wildcards.Schedule_TestActivity.mholperyear;
import static com.example.android.wildcards.Schedule_TestActivity.msleephrs;
import static com.example.android.wildcards.Schedule_TestActivity.mworkhrs;
import static com.example.android.wildcards.Schedule_TestActivity.old_sscore;
import static com.example.android.wildcards.jsonparsing.fetchEarthquakeData;

public class ImprovementActivity extends AppCompatActivity {

    private String response="";
    //private static final String USGS_REQUEST_URL =
        //    "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";

    private void setString(String diff){
        if(diff.contains("-")){
            response=diff.substring(1)+" Hours Less";
        }
        else if(diff.matches("0")){
            response="Hurray!!! No Improvement Needed";
        }
        else{
            response=diff+" Hours More";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improvement);

        /*EarthquakeAsyncTask task=new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);*/


        //private void updateUi(Event earthquake) {
        TextView sscore=(TextView)findViewById(R.id.old_sscore_text);
        sscore.setText("Your Score Is "+old_sscore+" Out Of 21.");

        setString(msleephrs);
        TextView sleepTextView = (TextView) findViewById(R.id.res_text1);
        sleepTextView.setText(response);

        setString(mworkhrs);
        TextView workTextView = (TextView) findViewById(R.id.res_text2);
        workTextView.setText(response);

        setString(mfreetime);
        TextView freeTextView = (TextView) findViewById(R.id.res_text3);
        freeTextView.setText(response);

        setString(mholperyear);
        TextView holTextView = (TextView) findViewById(R.id.res_text4);
        holTextView.setText(response);
        //}
    }

    /*private class EarthquakeAsyncTask extends AsyncTask<String,Void,Event> {

        @Override
        protected Event doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            Event result = jsonparsing.fetchEarthquakeData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(Event result){
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }
            updateUi(result);
        }
    }*/
}
