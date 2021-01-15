package com.junru.findyourmensa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.time.LocalDate;

import static com.junru.findyourmensa.ListDAO.*;


public class DishPlanActivity extends AppCompatActivity {

    String db_name = "mensa_db.db";
    ListDAO listdao;
    List<Mensa> open_time_list;
    TextView time_view;
    private ImageButton button;

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        open_time_list = listdao.getSearchResult(mensaName);

        time_view = findViewById(R.id.hours);

        ArrayAdapter<CharSequence> adapter = createAdapterHtml(open_time_list);

        time_view.setText(open_time_list.get(0).getTime()); //display opening hours

        Button today_button = findViewById(R.id.button2);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("DD.MM.YYYY[HH:mm:ss]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
        String todayStr = today.format(formatter);
        today_button.setText(todayStr + "\n"+"today"); //display the date of today on button
        today_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v;
                String dateWeek = b.getText().toString() + "\n" + mensaName;
                openDayPlanActivity(dateWeek);
            }

        });

        Button yesterday_button = findViewById(R.id.button1);
        LocalDate yesterday = today.minusDays(1);
        DayOfWeek dow1 = yesterday.getDayOfWeek();
        String yesterdayStr = yesterday.format(formatter);
        yesterday_button.setText(yesterdayStr + "\n" + dow1);//display the date and day of week of yesterday on button
        yesterday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v;
                String dateWeek = b.getText().toString() + "\n" + mensaName;
                openDayPlanActivity(dateWeek);
            }

        });

        Button tmr_button = findViewById(R.id.button3);
        LocalDate tmr = today.plusDays(1);
        DayOfWeek dow2 = tmr.getDayOfWeek();
        String tmrStr = tmr.format(formatter);
        tmr_button.setText(tmrStr + "\n" + dow2);//display the date and day of week of tomorrow on button
        tmr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v;
                String dateWeek = b.getText().toString() + "\n" + mensaName;
                openDayPlanActivity(dateWeek); //go to DayPlanActivity
            }

        });

        Button tmr2_button = findViewById(R.id.button4);
        LocalDate tmr2 = today.plusDays(2);
        DayOfWeek dow3 = tmr2.getDayOfWeek();
        String tmr2Str = tmr2.format(formatter);
        tmr2_button.setText(tmr2Str + "\n" + dow3);
        tmr2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v;
                String dateWeek = b.getText().toString() + "\n" + mensaName;
                openDayPlanActivity(dateWeek);
            }

        });

        Button tmr3_button = findViewById(R.id.button5);
        LocalDate tmr3 = today.plusDays(3);
        DayOfWeek dow4 = tmr3.getDayOfWeek();
        String tmr3Str = tmr3.format(formatter);
        tmr3_button.setText(tmr3Str + "\n" + dow4);
        tmr3_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v;
                String dateWeek = b.getText().toString() + "\n" + mensaName;
                openDayPlanActivity(dateWeek);
            }

        });

        Button tmr4_button = findViewById(R.id.button6);
        LocalDate tmr4 = today.plusDays(4);
        DayOfWeek dow5 = tmr4.getDayOfWeek();
        String tmr4Str = tmr4.format(formatter);
        tmr4_button.setText(tmr4Str + "\n" + dow5);
        tmr4_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v;
                String dateWeek = b.getText().toString() + "\n" + mensaName;
                openDayPlanActivity(dateWeek);
            }

        });






        button = findViewById(R.id.help_button);
        button.setOnClickListener(new View.OnClickListener() {
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
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.page_2:
                        startActivity(new Intent(getApplicationContext(), FilterActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.page_3:
                        startActivity(new Intent(getApplicationContext(), FavouritesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void openDayPlanActivity(String dateWeek) {
        Intent intent = new Intent(this, DayPlanActivity.class);
        intent.putExtra(getPackageName(), dateWeek);
        startActivity(intent);
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