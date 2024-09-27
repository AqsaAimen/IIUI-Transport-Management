package com.example.transportfirebase;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.widget.SearchView;

import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    DatabaseReference firebaseReference;
    FirebaseDatabase firebaseDatabase;
    LinearLayoutManager manager;
    ArrayList<VehiclesData> data;
    RecyclerView recyclerView;
    AdapterClass myadapter;
    ImageButton search;
    SearchView searchView;
    ArrayList<VehiclesData> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search = (ImageButton) findViewById(R.id.searchbtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseReference = firebaseDatabase.getInstance().getReference().child("Entered Vehicles");
        recyclerView = findViewById(R.id.recyclerview4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.searchfield);
        list = new ArrayList<>();
        myadapter = new AdapterClass(this, list);
        recyclerView.setAdapter(myadapter);

search.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    VehiclesData vehiclesData = dataSnapshot.getValue(VehiclesData.class);
                    list.add(vehiclesData);
                }
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
});
    }




}

