package com.junru.findyourmensa;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DayPlanActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataModel> recyclerDataArrayList;
    private ImageButton button; //help button
    private ImageButton next_button;
    private ImageButton back_button;
    LocalDate date1;
    DayOfWeek dow1;
    LocalDate date2;
    DayOfWeek dow2;
    String dateWeek;
    String date;
    String week;
    ZoneId zid = ZoneId.of("Europe/Berlin");



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_plan);
        recyclerView=findViewById(R.id.recyclerView);


        //display date and day of week
        Intent intent = getIntent();
        String dateAndWeek = intent.getStringExtra(getPackageName());
        dateWeek = dateAndWeek.split("\n")[1].toUpperCase() + ", " + dateAndWeek.split("\n")[0];
        date = dateAndWeek.split("\n")[0];
        week = dateAndWeek.split("\n")[1].toUpperCase();

        TextView textViewDate = findViewById(R.id.date);
        textViewDate.setText(dateWeek);

        //display Mensa name
        String mensaName = dateAndWeek.split("\n")[2];
        TextView textViewMensaName = findViewById(R.id.mensa_name);
        textViewMensaName.setText(mensaName);



        button = findViewById(R.id.help_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelpActivity();
            }
        });

        back_button = findViewById(R.id.back_button); //go back triangle button
        back_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                date1 = LocalDate.parse(date, formatter).minusDays(1);
                if (date1.isAfter(LocalDate.now(zid).minusDays(2))){
                dow1 = date1.getDayOfWeek();
                date = date1.format(formatter);
                if (dow1 == LocalDate.now(zid).getDayOfWeek())
                    dateWeek = "TODAY" + ", " + date;
                else
                    dateWeek = dow1 + ", " + date;
                textViewDate.setText(dateWeek);//Change the display of date
                Parser parser = new Parser("https://openmensa.org/api/v2/canteens");
                Data data = new Data(parser, System.out);
                data.initialize();
                Canteen mensa = (Canteen) data.getCanteenByName("Dresden," + " " + mensaName).iterator().next();
                data.initializeMeals(mensa);
                List<Meal> mealToday = (List<Meal>) mensa.getMealsByDate(date);
                    // created new array list..
                recyclerDataArrayList=new ArrayList<>();

                // added data to array list

                boolean emp = mealToday.isEmpty();
                int mealNum = mealToday.size();
                if (emp == false) {
                    for(int i = 0; i < mealNum; i++){
                    recyclerDataArrayList.add(new DataModel(mealToday.get(i).getName(), Double.toString(mealToday.get(i).getStudentPrice()) + "€ [Stu] / " + Double.toString(mealToday.get(i).getEmployeePrice()) + "€", mensaName)); }//,R.drawable.ic_gfglogo));
                    //recyclerDataArrayList.add(new DataModel(mensaToday.get(1).toString())); //,R.drawable.ic_gfglogo));
                    //recyclerDataArrayList.add(new DataModel(mensaToday.get(2).toString()));//,R.drawable.ic_gfglogo));
                    //recyclerDataArrayList.add(new DataModel("Item4")); //,R.drawable.ic_gfglogo));
                    //recyclerDataArrayList.add(new DataModel("Item5")); //,R.drawable.ic_gfglogo));
                }
                else {
                    recyclerDataArrayList.add(new DataModel("Not available today", " ", mensaName)); //,R.drawable.ic_gfglogo));
                    recyclerDataArrayList.add(new DataModel("Not available today"," ", mensaName)); //,R.drawable.ic_gfglogo));
                    //recyclerDataArrayList.add(new DataModel("Not available today")); //,R.drawable.ic_gfglogo));
                    //recyclerDataArrayList.add(new DataModel("Item4")); //,R.drawable.ic_gfglogo));
                    //recyclerDataArrayList.add(new DataModel("Item5")); //,R.drawable.ic_gfglogo));
                }

                // added data from arraylist to adapter class.
                RecyclerViewAdapter adapter=new RecyclerViewAdapter(recyclerDataArrayList,DayPlanActivity.this);

                // setting grid layout manager to implement grid view.
                // in this method '2' represents number of columns to be displayed in grid view.
                GridLayoutManager layoutManager = new GridLayoutManager(DayPlanActivity.this,2);

                // at last set adapter to recycler view.
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                }
                else {

                }
            }
        });

        next_button = findViewById(R.id.next_button);//go next triangle button
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                date2 = LocalDate.parse(date, formatter).plusDays(1);
                if (date2.isBefore(LocalDate.now(zid).plusDays(5))) {
                    dow2 = date2.getDayOfWeek();
                    date = date2.format(formatter);
                    if (dow2 == LocalDate.now(zid).getDayOfWeek())
                        dateWeek = "TODAY" + ", " + date;
                    else
                        dateWeek = dow2 + ", " + date;
                    textViewDate.setText(dateWeek);
                    Parser parser = new Parser("https://openmensa.org/api/v2/canteens");
                    Data data = new Data(parser, System.out);
                    data.initialize();
                    Canteen mensa = (Canteen) data.getCanteenByName("Dresden," + " " + mensaName).iterator().next();
                    data.initializeMeals(mensa);
                    List<Meal> mealToday = (List<Meal>) mensa.getMealsByDate(date);
                    // created new array list..
                    recyclerDataArrayList = new ArrayList<>();

                    // added data to array list

                    boolean emp = mealToday.isEmpty();
                    int mealNum = mealToday.size();
                    if (emp == false) {
                        for(int i = 0; i < mealNum; i++){
                            recyclerDataArrayList.add(new DataModel(mealToday.get(i).getName(), Double.toString(mealToday.get(i).getStudentPrice()) + "€ [Stu] / " + Double.toString(mealToday.get(i).getEmployeePrice()) + "€", mensaName)); }//,R.drawable.ic_gfglogo));
                        //recyclerDataArrayList.add(new DataModel(mensaToday.get(1).toString())); //,R.drawable.ic_gfglogo));
                        //recyclerDataArrayList.add(new DataModel(mensaToday.get(2).toString()));//,R.drawable.ic_gfglogo));
                        //recyclerDataArrayList.add(new DataModel("Item4")); //,R.drawable.ic_gfglogo));
                        //recyclerDataArrayList.add(new DataModel("Item5")); //,R.drawable.ic_gfglogo));
                    }
                    else {
                        recyclerDataArrayList.add(new DataModel("Not available today", " ", mensaName)); //,R.drawable.ic_gfglogo));
                        recyclerDataArrayList.add(new DataModel("Not available today"," ", mensaName)); //,R.drawable.ic_gfglogo));
                        //recyclerDataArrayList.add(new DataModel("Not available today")); //,R.drawable.ic_gfglogo));
                        //recyclerDataArrayList.add(new DataModel("Item4")); //,R.drawable.ic_gfglogo));
                        //recyclerDataArrayList.add(new DataModel("Item5")); //,R.drawable.ic_gfglogo));
                    }

                    // added data from arraylist to adapter class.
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(recyclerDataArrayList,DayPlanActivity.this);

                    // setting grid layout manager to implement grid view.
                    // in this method '2' represents number of columns to be displayed in grid view.
                    GridLayoutManager layoutManager = new GridLayoutManager(DayPlanActivity.this,2);

                    // at last set adapter to recycler view.
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
                else {

                }
            }
        });


        //initialize mensa meal
        Parser parser = new Parser("https://openmensa.org/api/v2/canteens"); //create Parser
        Data data = new Data(parser, System.out); //create Data
        data.initialize(); // initialize Data
        Canteen mensa = (Canteen) data.getCanteenByName("Dresden," + " " + mensaName).iterator().next(); //got this certain canteen (getCanteenByName returns a set)
        data.initializeMeals(mensa); // initialized this mensa's meals
        List<Meal> mealToday = (List<Meal>) mensa.getMealsByDate(date);//got all of todays meals in this mensa


        // created new array list..
        recyclerDataArrayList = new ArrayList<>();

        // added data to array list

        boolean emp = mealToday.isEmpty();
        int mealNum = mealToday.size();
        if (emp == false) {
            for(int i = 0; i < mealNum; i++){
            recyclerDataArrayList.add(new DataModel(mealToday.get(i).getName(), Double.toString(mealToday.get(i).getStudentPrice()) + "€ [Stu] / " + Double.toString(mealToday.get(i).getEmployeePrice()) + "€", mensaName));
            }//,R.drawable.ic_gfglogo));
            //recyclerDataArrayList.add(new DataModel(mensaToday.get(1).toString())); //,R.drawable.ic_gfglogo));
            //recyclerDataArrayList.add(new DataModel(mensaToday.get(2).toString()));//,R.drawable.ic_gfglogo));
            //recyclerDataArrayList.add(new DataModel("Item4")); //,R.drawable.ic_gfglogo));
            //recyclerDataArrayList.add(new DataModel("Item5")); //,R.drawable.ic_gfglogo));
        }
        else {
            recyclerDataArrayList.add(new DataModel("Not available today", " ", mensaName)); //,R.drawable.ic_gfglogo));
            recyclerDataArrayList.add(new DataModel("Not available today"," ", mensaName)); //,R.drawable.ic_gfglogo));
            //recyclerDataArrayList.add(new DataModel("Not available today")); //,R.drawable.ic_gfglogo));
            //recyclerDataArrayList.add(new DataModel("Item4")); //,R.drawable.ic_gfglogo));
            //recyclerDataArrayList.add(new DataModel("Item5")); //,R.drawable.ic_gfglogo));
        }

        // added data from arraylist to adapter class.
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(recyclerDataArrayList,this);

        // setting grid layout manager to implement grid view.
        // in this method '2' represents number of columns to be displayed in grid view.
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);

        // at last set adapter to recycler view.
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


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

    private void openHelpActivity() {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }




}