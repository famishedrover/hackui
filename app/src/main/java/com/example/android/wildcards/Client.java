package com.example.android.wildcards;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sagarpreet chadha on 11-02-2017.
 */

public class Client {
    private static CNAPIInterface service;

    public static CNAPIInterface getService() {

        if (service == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(45, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request r = chain.request();
                            r = r.newBuilder().addHeader("device_type", "android").build();
                            return chain.proceed(r);
                        }
                    })
                    .build();

            Retrofit r = new Retrofit.Builder().baseUrl("http://quotesondesign.com").
                    addConverterFactory(GsonConverterFactory.create(
                            new GsonBuilder().create())).client(client)
                    .build();
            service = r.create(CNAPIInterface.class);
        }
        return service;
    }
}

