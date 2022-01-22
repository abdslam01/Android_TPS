package com.example.conn_innercomposant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Ex3 extends AppCompatActivity implements View.OnClickListener {

    Button button_sms, button_appel, button_web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercice3);

        // Grab buttons
        button_sms = (Button) findViewById(R.id.buttonSms);
        button_appel = (Button) findViewById(R.id.buttonAppel);
        button_web = (Button) findViewById(R.id.buttonWeb);
        // set listener to buttons
        button_sms.setOnClickListener(this);
        button_appel.setOnClickListener(this);
        button_web.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonSms:
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:0641236159")));
                break;
            case R.id.buttonAppel:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0641236159")));
                break;
            case R.id.buttonWeb:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://abahafid.me")));
                break;
        }
    }
}
