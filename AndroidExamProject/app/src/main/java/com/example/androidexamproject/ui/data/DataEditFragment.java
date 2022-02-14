package com.example.androidexamproject.ui.data;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidexamproject.R;
import com.example.androidexamproject.database.Database;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataEditFragment extends Fragment {

    private View mView;
    private Database db;

    private EditText evId, evAvenue, evVille, evLat, evLng, evDateD, evDateF, dvObservation;
    private Button buttonApplyChanges;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_add_data, container, false);

        evId = (EditText) mView.findViewById(R.id.editTextId);
        evAvenue = (EditText) mView.findViewById(R.id.editTextAvenue);
        evVille = (EditText) mView.findViewById(R.id.editTextVille);
        evLat = (EditText) mView.findViewById(R.id.editTextLat);
        evLng = (EditText) mView.findViewById(R.id.editTextLng);
        evDateD = (EditText) mView.findViewById(R.id.editTextDateDebut);
        evDateF = (EditText) mView.findViewById(R.id.editTextDateFin);
        dvObservation = (EditText) mView.findViewById(R.id.editTextObservation);
        buttonApplyChanges = (Button) mView.findViewById(R.id.buttonApplyChanges);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new Database(getContext());
        if(getArguments().getString("CODE").equals("edit")) {
            Cursor cursor = db.selectDataById(getArguments().getInt("ID"));
            cursor.moveToFirst();

            evId.setText(String.valueOf(cursor.getInt(0)));
            evAvenue.setText(cursor.getString(1));
            evVille.setText(cursor.getString(2));
            evLat.setText(String.valueOf(cursor.getDouble(3)));
            evLng.setText(String.valueOf(cursor.getDouble(4)));
            evDateD.setText(cursor.getString(5));
            evDateF.setText(cursor.getString(6));
            dvObservation.setText(String.valueOf(cursor.getString(7)));
            cursor.close();
        }else if(getArguments().getString("CODE").equals("add")) {
            String ville = getArguments().getString("ville");
            String avenue = getArguments().getString("avenue");
            String lat = getArguments().getString("lat");
            String lng = getArguments().getString("lng");
            String dates = getArguments().getString("dates");

            evId.setText(String.valueOf(db.getLastInsertedId() + 1));
            evAvenue.setText(avenue);
            evVille.setText(ville);
            evLat.setText(lat);
            evLng.setText(lng);
            evDateD.setText(dates);
            evDateF.setText(dates);
        }

        buttonApplyChanges.setOnClickListener( view1 -> {
            if(getArguments().getString("CODE").equals("edit")) {
                db.updateData(
                        Integer.parseInt(evId.getText().toString()), evAvenue.getText().toString(),
                        evVille.getText().toString(), Float.parseFloat(evLat.getText().toString()),
                        Float.parseFloat(evLng.getText().toString()),
                        evDateD.getText().toString(), evDateF.getText().toString(),
                        dvObservation.getText().toString()
                );
                Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
            }else if (getArguments().getString("CODE").equals("add")) {
                db.insertData(
                        Integer.parseInt(evId.getText().toString()), evAvenue.getText().toString(),
                        evVille.getText().toString(), Float.parseFloat(evLat.getText().toString()),
                        Float.parseFloat(evLng.getText().toString()),
                        evDateD.getText().toString(), evDateF.getText().toString(),
                        dvObservation.getText().toString()
                );
                Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_LONG).show();
            }
            getActivity().getSupportFragmentManager()
                    .beginTransaction().replace(R.id.nav_host_fragment_content_main, new DataFragment())
                    .commit();
        });

        db.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}