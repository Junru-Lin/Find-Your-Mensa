package com.junru.findyourmensa;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<DataModel> courseDataArrayList;
    private Context mcontext;
    FavouritesDAO favouritesdao;
    String db_name = "mensa_db.db";



    public RecyclerViewAdapter(ArrayList<DataModel> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        DataModel DataModel = courseDataArrayList.get(position);
        holder.text.setText(DataModel.getText());
        holder.text.setEllipsize(TextUtils.TruncateAt.END);
        holder.text.setMaxLines(8);
        holder.price.setText(DataModel.getPrice());
        holder.allergenes.setText(DataModel.getAllergenes());
        //holder.image.setImageResource(recyclerData.getImgid());
        //add to favourite button
        holder.like_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                holder.like_button.setEnabled(false); //change button appearance after click
                String Desc= DataModel.getText();
                String Title = DataModel.getMensaName(); //to store MensaName
                String Price = DataModel.getPrice();
                String Allergenes =DataModel.getAllergenes();

                Favourite NewFavourite = new Favourite();
                NewFavourite.setDesc(Desc);
                NewFavourite.setTitle(Title);
                NewFavourite.setPrice(Price);
                NewFavourite.setAller(Allergenes);

                //to connect favouritedao to db
                AppDatabase database =
                        Room.databaseBuilder(mcontext.getApplicationContext(), AppDatabase.class, db_name)
                                .allowMainThreadQueries()
                                .build(); //connect with the database

                favouritesdao = database.getFavouritesDAO();
                favouritesdao.insert(NewFavourite);//insert in database

            }
        });


    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private TextView price;
        private ImageButton like_button;
        private TextView allergenes;
        //private TextView mensaName;
        //private ImageView image;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            like_button = itemView.findViewById(R.id.like1);
            allergenes = itemView.findViewById(R.id.allergenes);
            //mensaName = itemView.findViewById(R.id.mensa_name);
            //image = itemView.findViewById(R.id.imageView);


        }
    }





}
