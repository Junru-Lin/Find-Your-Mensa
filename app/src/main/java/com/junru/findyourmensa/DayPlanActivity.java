package com.junru.findyourmensa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DayPlanActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataModel> recyclerDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_plan);
        recyclerView=findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        String dateAndWeek = intent.getStringExtra(getPackageName()).split("\n")[1].toUpperCase() + ", " + intent.getStringExtra(getPackageName()).split("\n")[0];

        TextView textViewDate = findViewById(R.id.date);
        textViewDate.setText(dateAndWeek);

        String mensaName = intent.getStringExtra(getPackageName()).split("\n")[2];
        TextView textViewMensaName = findViewById(R.id.mensa_name);
        textViewMensaName.setText(mensaName);

        // created new array list..
        recyclerDataArrayList=new ArrayList<>();

        // added data to array list

        recyclerDataArrayList.add(new DataModel("Item1")); //,R.drawable.ic_gfglogo));
        recyclerDataArrayList.add(new DataModel("Item2")); //,R.drawable.ic_gfglogo));
        recyclerDataArrayList.add(new DataModel("Item3")); //,R.drawable.ic_gfglogo));
        recyclerDataArrayList.add(new DataModel("Item4")); //,R.drawable.ic_gfglogo));
        recyclerDataArrayList.add(new DataModel("Item5")); //,R.drawable.ic_gfglogo));

        // added data from arraylist to adapter class.
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(recyclerDataArrayList,this);

        // setting grid layout manager to implement grid view.
        // in this method '2' represents number of columns to be displayed in grid view.
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);

        // at last set adapter to recycler view.
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}