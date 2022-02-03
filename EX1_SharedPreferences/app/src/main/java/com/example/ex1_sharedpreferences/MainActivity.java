package com.example.ex1_sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editName, editEmail;
    private Button buttonEnregister, buttonCharger, buttonEffacer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = (EditText) findViewById(R.id.editTextNom);
        editEmail =(EditText) findViewById(R.id.editTextEmail);
        buttonEnregister = (Button) findViewById(R.id.buttonEnregister);
        buttonEffacer = (Button) findViewById(R.id.buttonEffacer);
        buttonCharger = (Button) findViewById(R.id.buttonCharger);

        // set listeners
        buttonEnregister.setOnClickListener(this);
        buttonEffacer.setOnClickListener(this);
        buttonCharger.setOnClickListener(this);
    }

    public void onClick(View v) {
        SharedPreferences pref = getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();

        switch(v.getId()) {
            case R.id.buttonEnregister:
                edit.putString("nom", editName.getText().toString());
                edit.putString("email", editEmail.getText().toString());
                edit.commit();
                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonCharger:
                editName.setText(pref.getString("nom", "No Name Found"));
                editEmail.setText(pref.getString("email", "No Email Found"));
                Toast.makeText(getApplicationContext(), "Charged Successfully", Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonEffacer:
                editName.setText("");
                editEmail.setText("");
                edit.clear();
                edit.commit();
                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();
                break;
        }
    }
}