package com.example.webview;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
      //  webView.loadUrl("https://www.youtube.com/watch?v=PXGycbkbtW0");
        webView.loadData("<html><body><h1>Hi there</h1><p>this is my website</p></body></html>","text/html","UTF-8");
    }
}