package com.junru.findyourmensa;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

// Extends the Adapter class to RecyclerView.Adapter
// and implement the unimplemented methods
public class AllergAdapter extends RecyclerView.Adapter<AllergAdapter.RecyclerViewHolder> {

    private ArrayList<DataModel> courseDataArrayList;
    private Context mcontext;



    public AllergAdapter(ArrayList<DataModel> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }


    @NonNull
    @Override
    public AllergAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allerg_item, parent, false);
        return new AllergAdapter.RecyclerViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull AllergAdapter.RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        DataModel DataModel = courseDataArrayList.get(position);
        holder.allergenes.setText(DataModel.getText());

    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView allergenes;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            allergenes = itemView.findViewById(R.id.allergenes);


        }
    }


}

