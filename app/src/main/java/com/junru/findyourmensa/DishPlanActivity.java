package com.junru.findyourmensa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class DishPlanActivity extends AppCompatActivity {

    String db_name = "mensa_opentime.db";
    ListDAO listdao;
    List<Mensa> mensa_list;
    TextView time_view;
    
    private ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_plan);

        Intent intent = getIntent();
        String mensaName = intent.getStringExtra(getPackageName());

        TextView textViewMensaName = findViewById(R.id.mensa_name);
        textViewMensaName.setText(mensaName);

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

        mensa_list = listdao.getSearchResult(mensaName);

        time_view = findViewById(R.id.hours);

        ArrayAdapter<CharSequence> adapter = createAdapterHtml(mensa_list);

        time_view.setText(mensa_list.get(0).getTime());

        button = findViewById(R.id.help_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openHelpActivity();
            }
            
        });

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.page_1:
                        startActivity(new Intent(getApplicationContext(), ListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_2:
                        startActivity(new Intent(getApplicationContext(), FilterActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_3:
                        startActivity(new Intent(getApplicationContext(), FavouritesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
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