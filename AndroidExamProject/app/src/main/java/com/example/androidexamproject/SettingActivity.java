package com.example.androidexamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidexamproject.database.Database;
import com.example.androidexamproject.database.FireDatabase;

public class SettingActivity extends AppCompatActivity {

    private Button buttonSync, buttonDownload;
    private Database db;
    private FireDatabase fireDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        buttonSync = (Button) findViewById(R.id.buttonSync);
        buttonDownload = (Button) findViewById(R.id.buttonDownload);
        db = new Database(getApplicationContext());
        fireDB = new FireDatabase();

        buttonSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireDB.setAllData(db.selectData());
                Toast.makeText(getApplicationContext(), "All Data Synced Successfully", Toast.LENGTH_LONG)
                        .show();
            }
        });

        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireDB.getAllData(db);
                Toast.makeText(getApplicationContext(), "All Data Downloaded Successfully", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}