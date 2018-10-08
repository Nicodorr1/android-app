package com.example.nicodorr.nostress;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class Activity3 extends AppCompatActivity {
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the intent
        Intent intent = getIntent();
        String myValue = intent.getStringExtra("parameter name");
        String content = intent.getStringExtra("parameter name1");
        int lik = intent.getIntExtra("params2",0);

        //Display the value to the screen
        TextView myText = (TextView) findViewById(R.id.text_make);
        myText.setText("" +myValue);

        TextView likes = (TextView) findViewById(R.id.text_likes);
        likes.setText("Likes: " +lik);

        //pou webview a
        mWebView = (WebView)findViewById(R.id.webView);
        String text = "<html><head>"
                +"<style type=\'text/css\'>body{color: #000; font-size: 18px;}"
                +"</style></head>"
                +"<body>"
                +"<p align='justify'>"
                +content
                +"</p>"
                +"</body></html>";
        mWebView.loadData(text,"text/html","UTF-8");
        mWebView.setBackgroundColor(0x00000000);
    }

}
