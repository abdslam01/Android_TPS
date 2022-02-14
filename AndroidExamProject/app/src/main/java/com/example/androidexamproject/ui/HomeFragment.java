package com.example.androidexamproject.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidexamproject.R;
import com.example.androidexamproject.database.Database;
import com.example.androidexamproject.helpers.Permission;
import com.example.androidexamproject.ui.data.DataEditFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private View mView;
    private SearchView searchView;
    private Database db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mView = getLayoutInflater().inflate(R.layout.activity_maps, null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = mView.findViewById(R.id.map_search);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addressList;

                if(!Permission.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), "Please, check your network", Toast.LENGTH_LONG).show();
                    return false;
                }
                if(!location.equals("")) {
                    Geocoder geocoder = new Geocoder(getContext());
                    try{
                        mMap.clear();
                        addressList = geocoder.getFromLocationName(location, 1);
                        LatLng latLng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    }catch(Exception e) {
                        Toast.makeText(getContext(), "Location not found", Toast.LENGTH_LONG).show();
                    }
                }else
                    Toast.makeText(getContext(), "Search input is empty", Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Toast.makeText(
                        getContext(),
                        "Lat : " + latLng.latitude + " , "
                                + "Long : " + latLng.longitude,
                        Toast.LENGTH_LONG).show();
            }
        });
        drawPathMarkers();
    }

    @SuppressLint("Range")
    private void drawPathMarkers(){
        db = new Database(getContext());
        Cursor cursor = db.selectData();

        while(cursor.moveToNext()){
            mMap.addMarker(new MarkerOptions().position(
                    new LatLng(
                            cursor.getFloat(cursor.getColumnIndex("lat")),
                            cursor.getFloat(cursor.getColumnIndex("lng"))
                    )
            ).title(cursor.getString(cursor.getColumnIndex("ville")))
            .snippet(cursor.getString(cursor.getColumnIndex("avenue")))
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }

        cursor.close();
        db.close();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}