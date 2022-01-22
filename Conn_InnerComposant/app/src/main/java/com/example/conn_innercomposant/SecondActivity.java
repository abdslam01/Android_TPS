package com.example.conn_innercomposant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        textView = (TextView) findViewById(R.id.textViewRecu);
        try {
            String messageRecu = getIntent().getStringExtra("message");
            textView.setText(messageRecu.isEmpty() ? "Saisie Vide" : messageRecu);
        } catch(Exception e){
            textView.setText(e.getMessage());
        }
    }
}