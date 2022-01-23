package com.example.album_images;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OneImageActivity extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_image);

        imageView = (ImageView) findViewById(R.id.id_full_image);

        try {
            String messageRecu = getIntent().getStringExtra("source");
            int drawable = getResources().getIdentifier(messageRecu, "drawable", getPackageName());
            imageView.setImageResource(drawable);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
