package com.example.simplefilesexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayoutFiles;
    private static final String STARTING_FOLDER_NAME = "/storage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayoutFiles = (LinearLayout) findViewById(R.id.linearLayoutFiles);

        if(checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE))
            try {
                showFilesFromFolder(STARTING_FOLDER_NAME);
            }catch (Exception e){
                e.printStackTrace();
            }
        else
            askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1);
    }

    private void showFilesFromFolder(String folder) {
        File fd = new File(folder);
        String parentFolder = fd.getParent();
        setTitle(fd.getAbsolutePath());
        try {
            linearLayoutFiles.removeAllViews();

            // Add return to parent folder
            if (!fd.getName().equals("/")) { // if we're not in the "/" directory
                View one_file = getLayoutInflater().inflate(R.layout.one_file_layout, null);
                TextView textView = one_file.findViewById(R.id.textView);
                ImageView imageView = one_file.findViewById(R.id.imageView);
                textView.setText("..");
                imageView.setImageResource(R.drawable.folder_icon);
                one_file.setOnClickListener(view -> showFilesFromFolder(parentFolder));
                linearLayoutFiles.addView(one_file);
            }

            for (File file : fd.listFiles()) {
                View one_file = getLayoutInflater().inflate(R.layout.one_file_layout, null);
                TextView textView = one_file.findViewById(R.id.textView);
                ImageView imageView = one_file.findViewById(R.id.imageView);

                textView.setText(file.getName());
                if (file.isDirectory()) {
                    //The default icon is set to: FILE ICON
                    imageView.setImageResource(R.drawable.folder_icon);
                    one_file.setOnClickListener(view -> showFilesFromFolder(file.getAbsolutePath()));
                }

                linearLayoutFiles.addView(one_file);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),
                    "you don't have access to view this folder", Toast.LENGTH_LONG).show();
            showFilesFromFolder(parentFolder);
        }
    }

    private boolean checkPermission(String permission) {
        return PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(this, permission);
    }

    private void askForPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // granResults[i] equals -1 or 0, mean respectively access denied or access granted
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults[0] == 0){
            showFilesFromFolder(STARTING_FOLDER_NAME);
            Toast.makeText(getApplicationContext(), "Access granted", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(getApplicationContext(), "Access denied", Toast.LENGTH_LONG).show();
    }
}