package com.example.android.wildcards;

import java.util.ArrayList;

/**
 * Created by sagarpreet chadha on 11-02-2017.
 */

public class EventResponse {

    String title ;
    String content ;

    public EventResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
