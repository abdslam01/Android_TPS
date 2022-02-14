package com.example.androidexamproject.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.androidexamproject.R;
import com.example.androidexamproject.database.Database;
import com.example.androidexamproject.helpers.Permission;
import com.example.androidexamproject.ui.data.DataEditFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DrawPathFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private View mView;
    private Database db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mView = getLayoutInflater().inflate(R.layout.fragment_draw_path, null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_for_draw_path);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.clear();
    }

    @SuppressLint("Range")
    private void drawPathMarkers(int from, int to){
        db = new Database(getContext());
        Cursor cursor = db.selectData();

        while(cursor.moveToNext()){
            if(cursor.getString(0).equals(String.valueOf(from))){
                mMap.addMarker(new MarkerOptions().position(
                        new LatLng(
                                cursor.getFloat(cursor.getColumnIndex("lat")),
                                cursor.getFloat(cursor.getColumnIndex("lng"))
                        )
                ).title(cursor.getString(cursor.getColumnIndex("ville")))
                        .snippet("Le Depart")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }else if(cursor.getString(0).equals(String.valueOf(to))){
                mMap.addMarker(new MarkerOptions().position(
                        new LatLng(
                                cursor.getFloat(cursor.getColumnIndex("lat")),
                                cursor.getFloat(cursor.getColumnIndex("lng"))
                        )
                ).title(cursor.getString(cursor.getColumnIndex("ville")))
                        .snippet("L'arrivée")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            mMap.addMarker(new MarkerOptions().position(
                    new LatLng(
                            cursor.getFloat(cursor.getColumnIndex("lat")),
                            cursor.getFloat(cursor.getColumnIndex("lng"))
                    )
            ).title(cursor.getString(cursor.getColumnIndex("ville")))
                    .snippet(cursor.getString(cursor.getColumnIndex("avenue")))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

        cursor.close();
        db.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}