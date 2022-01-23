package com.example.album_images;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<ImageView> imageViews;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab and Set Click Listener to all ImageViews
        imageViews = new ArrayList<ImageView>();
        for(int i=1; i<=10; i++) {
            int id = getResources().getIdentifier("img_id"+i, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(id);
            imageViews.add(iv);
            iv.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        intent = new Intent(view.getContext(), OneImageActivity.class);
        intent.putExtra("source", ((ImageView) view).getContentDescription().toString());
        startActivity(intent);
    }
}