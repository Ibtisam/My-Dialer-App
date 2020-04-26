package com.example.mydialerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialerActivity extends AppCompatActivity {
    private TextView telNumber;
    private Intent dial;
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

    public void num_b_action(View v) {
        //typecasting View as Button
        Button b = (Button) v;
        //getting text of button and appending it to TextView
        telNumber.append(b.getText());
    }

    public void del_b_action(View v) {
        String num = telNumber.getText().toString();
        //checking for null string
        if (num.length() > 0) {
            //creating a new string by eliminating the last number
            String newNum = num.substring(0, num.length() - 1);
            //setting the number to TextView
            telNumber.setText(newNum);
        }
    }

    public void dial_b_action(View v) {
        dial = new Intent(Intent.ACTION_CALL);
        dial.setData(Uri.parse("tel:" + telNumber.getText()));
        //checking for required permission
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            // will be executed if user denied the permission request first time
            if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                showAlertDialog("Permission Request Justification", "App requires this permission to place calls");
                // requesting permission
                //requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 99);
            } else {
                // will be executed at first run
                // No explanation needed, just request for permission
                requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 99);
            }
        } else {
            startActivity(dial);
        }
    }

    public void showAlertDialog(String title, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // request permission when user presses the OK button of dialog
                requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 99);
            }
        });
        // do nothing when user presses cancel button on dialog
        builder.setNegativeButton("Cancel",null);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(text);
        // Set other dialog properties

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 99: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    // start the dialer activity
                    startActivity(dial);
                } else {
                    // permission denied, boo!
                    // show dialog on why your app needs permission
                    showAlertDialog("Request Permission Justification", "To place calls using this dialer app, it requires CALL_PHONE permission");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


}
