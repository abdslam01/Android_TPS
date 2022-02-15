package com.example.androidexamproject.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.androidexamproject.R;
import com.example.androidexamproject.database.Database;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

public class DrawPathFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, RoutingListener {

    private GoogleMap mMap;
    private View mView;
    private Database db;
    private Button buttonDrawPath;
    private EditText editTextIdDepart, getEditTextIdArrivee;
    private final static int LOCATION_REQUEST_CODE = 23;
    protected LatLng start, end;
    protected int i_start, i_end;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mView = getLayoutInflater().inflate(R.layout.fragment_draw_path, null);
        db = new Database(getContext());
        return mView;
    }

    @SuppressLint("Range")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_for_draw_path);
        mapFragment.getMapAsync(this);

        editTextIdDepart = (EditText) mView.findViewById(R.id.editTextIdDepart);
        getEditTextIdArrivee = (EditText) mView.findViewById(R.id.editTextIdArrivee);
        buttonDrawPath = (Button) mView.findViewById(R.id.buttonDrawPath);
        buttonDrawPath.setOnClickListener(v -> {
            mMap.clear();

            i_start = Integer.parseInt(editTextIdDepart.getText().toString());
            i_end = Integer.parseInt(getEditTextIdArrivee.getText().toString());

            db = new Database(getContext());
            if(db.selectDataById(i_start).getCount() == 0)
                Toast.makeText(getContext(), "Depart id is incorrect...", Toast.LENGTH_LONG)
                        .show();
            else if(db.selectDataById(i_end).getCount() == 0)
                Toast.makeText(getContext(), "Arrival id is incorrect...", Toast.LENGTH_LONG)
                        .show();
            else {
                drawPathMarkers(i_start, i_end);
                start = null;
                for(int id : new Integer[]{i_start, i_end}) {
                    Cursor cur = db.selectDataById(id);
                    cur.moveToFirst();
                    if(start == null)
                        start = new LatLng(
                                cur.getFloat(cur.getColumnIndex("lat")),
                                cur.getFloat(cur.getColumnIndex("lng"))
                        );
                    else
                        end = new LatLng(
                                cur.getFloat(cur.getColumnIndex("lat")),
                                cur.getFloat(cur.getColumnIndex("lng"))
                        );
                }
                findRoutes(start, end);
            }
            db.close();
        });
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
        LatLng tmpLatLng;

        while(cursor.moveToNext()){
            tmpLatLng = new LatLng(
                    cursor.getFloat(cursor.getColumnIndex("lat")),
                    cursor.getFloat(cursor.getColumnIndex("lng"))
            );
            if(cursor.getString(0).equals(String.valueOf(from))){
                mMap.addMarker(new MarkerOptions().position(tmpLatLng).
                        title(cursor.getString(cursor.getColumnIndex("ville")))
                        .snippet("Le Depart")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }else if(cursor.getString(0).equals(String.valueOf(to))){
                mMap.addMarker(new MarkerOptions().position(tmpLatLng).
                        title(cursor.getString(cursor.getColumnIndex("ville")))
                        .snippet("L'arrivée")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }else {
                mMap.addMarker(new MarkerOptions().position(tmpLatLng)
                        .title(cursor.getString(cursor.getColumnIndex("ville")))
                        .snippet(cursor.getString(cursor.getColumnIndex("avenue")))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        }

        cursor.close();
        db.close();
    }

    public void findRoutes(LatLng Start, LatLng End)
    {
        if(Start==null || End==null)
            Toast.makeText(getContext(),"please set start and end locations",Toast.LENGTH_LONG).show();
        else {
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyDHCY5a7ZKFXnzL4ON-EcCbteejHffwP_o")
                    .build();
            routing.execute();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Start, 10));
        }
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(getContext(),"Finding Route...",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRoutingCancelled() {
        findRoutes(start,end);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        findRoutes(start,end);
    }

    @SuppressLint("Range")
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        PolylineOptions polyOptions = new PolylineOptions();
        PolylineOptions polyOptionsConstraint = new PolylineOptions();
        ArrayList<LatLng> locations = new ArrayList<>();
        Cursor cur;
        Boolean tmpBool;

        db = new Database(getContext());
        cur = db.selectData();
        while(cur.moveToNext()) {
            if(cur.getInt(0) != i_start && cur.getInt(0) != i_end)
                locations.add(new LatLng(
                        cur.getFloat(cur.getColumnIndex("lat")),
                        cur.getFloat(cur.getColumnIndex("lng"))
                ));
        }
        cur.close();
        db.close();

        //add route(s) to the map using polyline
        for (int i = 0; i < route.size(); i++) {
            if (i == shortestRouteIndex) {
                polyOptions.color(getResources().getColor(R.color.black));
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                mMap.addPolyline(polyOptions);
            }

            tmpBool = true;
            for(LatLng loc: locations) {
                if(PolyUtil.isLocationOnPath(loc, route.get(i).getPoints(), true, 30)){
                    tmpBool = false;
                }
            }
            if(tmpBool) {
                polyOptionsConstraint.color(getResources().getColor(R.color.purple_200));
                polyOptionsConstraint.width(7);
                polyOptionsConstraint.addAll(route.get(i).getPoints());
                mMap.addPolyline(polyOptionsConstraint);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}