package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.DiaDanhAdapter;
import adapter.RecentsAdapter;
import models.DiaDanh;


public class MainActivity extends AppCompatActivity {

    RecyclerView recentRecycler;
    RecentsAdapter recentsAdapter;
    ListView lvTopplaces;
    DiaDanhAdapter diaDanhAdapter;
    ArrayList<DiaDanh> diaDanhList;
    public static boolean DEBUG_MODE =BuildConfig.BUILD_TYPE.equals("debug");

    public static ArrayList<DiaDanh> diaDanhs;
    public static Databases db;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new Databases(this);
        db.openDataBase();

        linkView();
        loadData();
    }

    private void linkView() {
        lvTopplaces = findViewById(R.id.lvTopplaces);
    }

    private void loadData() {

        diaDanhs = db.getDiaDanh();
//        Cursor cursor = db.getDiaDanh();
//        while (cursor.moveToNext()){
//            diaDanhs.add(new DiaDanh(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4), cursor.getInt(5), db.getVedicleID(cursor.getInt(6))));
//        }
//        cursor.close();
        diaDanhAdapter = new DiaDanhAdapter(MainActivity.this, R.layout.top_places_row_item, diaDanhs);
        lvTopplaces.setAdapter(diaDanhAdapter);
    }

    private void setRecentRecycler(List<DiaDanh> recentsDataList){

        recentRecycler = findViewById(R.id.recent_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this, recentsDataList);
        recentRecycler.setAdapter(recentsAdapter);

    }

}




