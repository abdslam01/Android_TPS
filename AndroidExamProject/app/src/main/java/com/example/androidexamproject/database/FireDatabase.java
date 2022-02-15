package com.example.androidexamproject.database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireDatabase{
    private DatabaseReference databaseReference;


    public FireDatabase(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Chantier");
    }

    public void getAllData(Database db){
        databaseReference.addValueEventListener(new ValueEventListener() {
            String id, avenue, ville, lat, lng, dateD, dateF, observ;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    id = dataSnapshot.getKey();
                    avenue =dataSnapshot.child("avenue").getValue().toString();
                    ville = dataSnapshot.child("ville").getValue().toString();
                    lat = dataSnapshot.child("lat").getValue().toString();
                    lng = dataSnapshot.child("lng").getValue().toString();
                    dateD = dataSnapshot.child("dateD").getValue().toString();
                    dateF = dataSnapshot.child("dateF").getValue().toString();
                    observ = dataSnapshot.child("observ").getValue().toString();
                    db.insertData(
                            Integer.parseInt(id), avenue, ville,Float.parseFloat(lat),
                            Float.parseFloat(lng), dateD, dateF, observ
                    );
                }
                Log.e("a=>", "aa");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void setAllData(Cursor cursor){
        while(cursor.moveToNext()){
            String id = String.valueOf(cursor.getInt(0));
            Chantier chantier = new Chantier(
                cursor.getString(1),
                cursor.getString(2),
                String.valueOf(cursor.getDouble(3)),
                String.valueOf(cursor.getDouble(4)),
                cursor.getString(5),
                cursor.getString(6),
                String.valueOf(cursor.getString(7))
            );
            databaseReference.child(id).setValue(chantier);
        }
    }
}