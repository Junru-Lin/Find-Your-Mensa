package com.junru.findyourmensa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    private ImageButton button;

    String db_name = "mensa_db.db";
    FavouritesDAO favouritesdao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

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

        AppDatabase database =
                Room.databaseBuilder(this, AppDatabase.class, db_name)
                        .allowMainThreadQueries()
                        .build();

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

    public void onClickAddDataRecord(View view) {
        TextView description_view = findViewById(R.id.description);
        TextView title_view = findViewById(R.id.mensa_name);
        TextView price_view = findViewById(R.id.price);


        String Desc= description_view.getText().toString();
        String Title = title_view.getText().toString();
        String Price = price_view.getText().toString();


        Favourite NewFavourite = new Favourite();
        NewFavourite.setDesc(Desc);
        NewFavourite.setTitle(Title);
        NewFavourite.setPrice(Price);

        favouritesdao.insert(NewFavourite);

        recreate();
    }


    private void openHelpActivity() {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}