package com.example.mydialerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void open_explicit(View v){
        Intent intent = new Intent();
        //creating an explicit intent
        //intent.setClass(this, DialerActivity.class);
        intent.setAction("com.example.mydialerapp.MYDIALER");
        //setting data as Uri
        intent.setData(Uri.parse("tel:"));
        //starting activity
        startActivity(intent);
    }

    public void open_implicit(View v){
        Intent intent = new Intent();
        //creating an implicit intent, android will decide which activity to start based on intent info
        //android will open the default dialer app
        intent.setAction(Intent.ACTION_DIAL);
        //setting the data as Uri
        intent.setData(Uri.parse("tel:+923234520282"));
        //starting the activity
        startActivity(intent);
    }
}
