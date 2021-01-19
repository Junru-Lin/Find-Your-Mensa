package com.junru.findyourmensa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    private ImageButton button;
    List<Favourite> favourite_list;

    String db_name = "mensa_db.db";
    FavouritesDAO favouritesdao;

    RecyclerView favourite_view;
    private ArrayList<DataModel> recyclerDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        //open help page
        button = findViewById(R.id.help_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openHelpActivity();
            }
        });

        final File dbFile = this.getDatabasePath(db_name);

        if (!dbFile.exists()) {
            try {
                copyDatabaseFile(dbFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //access database
        AppDatabase database =
                Room.databaseBuilder(this, AppDatabase.class, db_name)
                        .allowMainThreadQueries()
                        .build();

        //to get favourites
        favouritesdao = database.getFavouritesDAO();
        favourite_list = favouritesdao.getAllFavourite();
        Integer favNum = favourite_list.size();

        favourite_view = findViewById(R.id.recyclerView);

        //display favourites in recyclerView
        recyclerDataArrayList = new ArrayList<>();
        for(int i = 0; i < favNum; i++){
        recyclerDataArrayList.add(new DataModel(favourite_list.get(i).getDesc()
                , favourite_list.get(i).getPrice()
                , favourite_list.get(i).getTitle()
                , favourite_list.get(i).getAller())); }

        FavRecyclerViewAdapter adapter = new FavRecyclerViewAdapter(recyclerDataArrayList, FavouritesActivity.this);

        // setting grid layout manager to implement grid view.
        // in this method '2' represents number of columns to be displayed in grid view.
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);

        // at last set adapter to recycler view.
        favourite_view.setLayoutManager(layoutManager);
        favourite_view.setAdapter(adapter);



    }

    private void copyDatabaseFile(String destinationPath) throws IOException {

        InputStream assetsDB = this.getAssets().open(db_name);
        OutputStream dbOut = new FileOutputStream(destinationPath);

        byte[] buffer = new byte[1024];

        int length;

        while ((length = assetsDB.read(buffer)) > 0) {

            dbOut.write(buffer, 0, length);
        }

        dbOut.flush();
        dbOut.close();
    }



    private void openHelpActivity() {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }


}