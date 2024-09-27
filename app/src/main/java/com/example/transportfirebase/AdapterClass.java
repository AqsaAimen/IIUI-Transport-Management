package com.example.transportfirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder>  {
ArrayList<VehiclesData> mlist;
    ArrayList<VehiclesData> fulllist;
Context context;
private AdapterClass myadapter;
public AdapterClass(Context context,ArrayList<VehiclesData> mlist){
    this.context=context;
    this.mlist=mlist;
    fulllist=new ArrayList<>(mlist);
}



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.listlayout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     VehiclesData data=mlist.get(position);
   holder.vehno.setText((Integer.toString(data.getVehno())));
     holder.vehname.setText(data.getVehname());
     holder.drivercnic.setText(data.getDrivercnic());
        Picasso.get().load(data.getImageurl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
    TextView vehno,vehname,drivercnic;
    ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vehno=itemView.findViewById(R.id.vehno);
            vehname=itemView.findViewById(R.id.vehname);
            drivercnic=itemView.findViewById(R.id.drivercnic);
            imageView=itemView.findViewById(R.id.veh_image);
        }
    }
   }