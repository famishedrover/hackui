package com.example.android.wildcards;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CausesActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_causes);

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);
        //   https://drive.google.com/drive/folders/0B_wShFEhFWJDZHdpVzUyX0Fja0E
        String url = "https://depression.org.nz/the-causes/your-past/";
        //https://drive.google.com/folderview?id=0B5WAUv2qkI6zZHBuVWNBNWNMYlE&usp=sharing
        //String url="https://www.instagram.com/?hl=en";
        webView.loadUrl(url);

        progress = new ProgressDialog(this);
        progress.setMessage("Loading");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

            return true;

        }
    }
}