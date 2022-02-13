package com.example.androidexamproject.ui.data;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidexamproject.R;
import com.example.androidexamproject.database.Database;

import java.util.Date;

public class DataFragment extends Fragment {

    private Database db;
    private View mView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mView = getLayoutInflater().inflate(R.layout.main_data, null);
        mView.findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataEditFragment fragment = new DataEditFragment();
                Bundle args = new Bundle();
                args.putString("CODE", "add");
                fragment.setArguments(args);

                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.nav_host_fragment_content_main, fragment)
                        .commit();
            }
        });
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadDataLayout();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void loadDataLayout(){
        db = new Database(getContext());
        //db.insertData(1, "A st.", "Rabat", 10.5f, 10, "10-01-2022", "20-21-2022", "Observation");
        //db.insertData(2, "2 A st.", "2 Rabat", 10.5f, 10, "10-01-2022", "20-21-2022", "Observation");

        LinearLayout linearLayout_data = (LinearLayout) mView.findViewById(R.id.linearLayout_data);

        linearLayout_data.removeAllViews();

        Cursor cursor = db.selectData();
        if (cursor.getCount() == 0)
            Toast.makeText(getContext(), "No Data In The Table", Toast.LENGTH_LONG).show();
        else{
            while(cursor.moveToNext()){
                View view = getLayoutInflater().inflate(R.layout.line_data, null);

                TextView textViewId = (TextView) view.findViewById(R.id.textViewId);
                TextView textViewAvenue = (TextView) view.findViewById(R.id.textViewAvenue);
                TextView textViewVille = (TextView) view.findViewById(R.id.textViewVille);

                int id = cursor.getInt(0);
                ImageView editLineBtn = (ImageView) view.findViewById(R.id.editLineBtn);
                editLineBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DataEditFragment fragment = new DataEditFragment();
                        Bundle args = new Bundle();
                        args.putInt("ID", id);
                        args.putString("CODE", "edit");
                        fragment.setArguments(args);

                        getActivity().getSupportFragmentManager()
                                .beginTransaction().replace(R.id.nav_host_fragment_content_main, fragment)
                                .commit();
                    }
                });

                ImageView deleteBtn = (ImageView) view.findViewById(R.id.deleteBtn);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.deleteData(id);
                        loadDataLayout();
                    }
                });

                textViewId.setText(String.valueOf(cursor.getInt(0)));
                textViewAvenue.setText(cursor.getString(1));
                textViewVille.setText(cursor.getString(2));
                linearLayout_data.addView(view);
            }
        }

        cursor.close();
        db.close();
    }
}