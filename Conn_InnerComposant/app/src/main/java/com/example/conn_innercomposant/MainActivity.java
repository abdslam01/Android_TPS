package com.example.conn_innercomposant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button, btnex3;
    private EditText editText;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab the button and the text input
        button = (Button) findViewById(R.id.button);
        btnex3 = (Button) findViewById(R.id.buttonEx3);
        editText = (EditText) findViewById(R.id.editTextMessage);

        // Set Listener to the Button
        button.setOnClickListener(this);
        btnex3.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v.getId() == button.getId()) {
            intent = new Intent(v.getContext(), SecondActivity.class);
            intent.putExtra("message", editText.getText().toString());
            startActivity(intent);
            Toast.makeText(this, "le message a été envoyer avec success", Toast.LENGTH_LONG).show();
        }else if(v.getId() == btnex3.getId())
            startActivity(new Intent(v.getContext(), Ex3.class));
    }
}