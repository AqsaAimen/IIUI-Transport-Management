package com.example.transportfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    Button vehicleinfo;
    Button vehleave;
    ImageButton search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vehicleinfo=(Button)findViewById(R.id.detailpess);
        vehicleinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentinfo=new Intent(MainActivity.this,VehicleEntrance.class);
                startActivity(intentinfo);
            }
        });
        vehleave=(Button)findViewById(R.id.vehleav);
        vehleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlea=new Intent(MainActivity.this,VehicleLeaving.class);
                startActivity(intentlea);

            }
        });
        search=(ImageButton)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsearch=new Intent(MainActivity.this,Search.class);
                startActivity(intentsearch);
            }
        });

    }
    }
