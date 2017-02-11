package com.example.android.wildcards;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sagarpreet chadha on 11-02-2017.
 */

public interface CNAPIInterface {
    @GET("/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=1")
    Call<ArrayList<EventResponse>> getEvents();
}
