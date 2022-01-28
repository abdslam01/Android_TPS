package com.example.smssender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumero, editTextMessage;
    private TextView textViewResult;
    private Button buttonEnvoyer;
    private final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab Elements
        editTextNumero = (EditText) findViewById(R.id.editTextPhone);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        buttonEnvoyer = (Button) findViewById(R.id.buttonEnvoyer);

        // Disable the button if the SMS permission not granted
        buttonEnvoyer.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS))
            buttonEnvoyer.setEnabled(true);
        else
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);

        // Set Listener to The Button
        buttonEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms();
            }
        });
    }

    private void sendSms() {
        String number = editTextNumero.getText().toString();
        String message = editTextMessage.getText().toString();

        if(!Pattern.compile("^(\\+2126|06)\\d{8}$").matcher(number).find())
            textViewResult.setText("Le numero doit etre composé de 8 chiffre apres (+2126 ou 06)");
        else if(message.isEmpty())
            textViewResult.setText("Le message est vide!");
        else if(checkPermission(Manifest.permission.SEND_SMS)){
                SmsManager.getDefault()
                        .sendTextMessage(number, null, message, null, null);
                textViewResult.setText("");
                Toast.makeText(getApplicationContext(), "Le message à été envoyer", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(getApplicationContext(), "Pas de permission!", Toast.LENGTH_LONG).show();
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED;
    }
}