package com.example.menu_restaurant;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private ArrayList<String> supportedProducts;
    private String productToShow;

    public SecondActivity() {
        String[] products = {"pizzas"};
        supportedProducts = new ArrayList<>();
        for(String p: products)
            supportedProducts.add(p);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // specify product to show
        productToShow = getIntent().getStringExtra("products_to_show")
                .toLowerCase();
        if(!supportedProducts.contains(productToShow)) {
            setContentView(R.layout.activity_no_data_found);
            return;
        }
        setContentView(R.layout.activity_second);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quit:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
