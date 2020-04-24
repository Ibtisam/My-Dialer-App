package com.example.mydialerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialerActivity extends AppCompatActivity {
    private TextView telNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);
        //getting reference of TextView
        telNumber = findViewById(R.id.textView);

        //getting the intent
        Intent data = getIntent();
        //getting data parsed as Uri
        Uri uri = data.getData();
        //converting the Uri to string
        String telNum = uri.toString();
        //setting telNum on TextView
        telNumber.setText(telNum.substring(4));
    }

    public void num_b_action(View v){
        //typecasting View as Button
        Button b = (Button)v;
        //getting text of button and appending it to TextView
        telNumber.append(b.getText());
    }

    public void del_b_action(View v){
        String num = telNumber.getText().toString();
        //checking for null string
        if(num.length()>0) {
            //creating a new string by eliminating the last number
            String newNum = num.substring(0, num.length() - 1);
            //setting the number to TextView
            telNumber.setText(newNum);
        }
    }

    public void dial_b_action(View v){
        Intent dial = new Intent(Intent.ACTION_CALL);
        dial.setData(Uri.parse("tel:"+telNumber.getText()));
        //checking for required permission
        if(checkSelfPermission(Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            //requesting for required permission
            requestPermissions(new String[]{"android.permission.CALL_PHONE"},99);
            startActivity(dial);
        }else {
            startActivity(dial);
        }
    }

}
