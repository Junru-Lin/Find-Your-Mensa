package com.junru.findyourmensa;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.text.Html;
import android.text.Spanned;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    String db_name = "mensa_db.sqlite";
    ListDAO listdao;
    List<Mensa> mensa_list;
    ListView list_view;

    private ImageButton button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

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

        listdao = database.getListDAO();
        mensa_list = listdao.getAllMensa();

        list_view = findViewById(R.id.list);

        ArrayAdapter<CharSequence> adapter = createAdapterHtml(mensa_list);
        list_view.setAdapter(adapter);

        button = findViewById(R.id.help_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelpActivity();
            }
        });
    }

    private void openHelpActivity() {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
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

    private ArrayAdapter<CharSequence> createAdapterHtml(List<Mensa> u_list) {

        Spanned[] html_array = new Spanned[u_list.size()];

        for(int i = 0 ; i < u_list.size(); i++) {
            html_array[i] = Html.fromHtml(u_list.get(i).getName());
        }

        ArrayAdapter<CharSequence> my_adapter =
                new ArrayAdapter<CharSequence>(this, R.layout.list_item, html_array);

        return my_adapter;

    }
}


