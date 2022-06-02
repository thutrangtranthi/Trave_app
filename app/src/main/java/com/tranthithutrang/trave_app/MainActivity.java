package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        addEvent();
    }

    private void addEvent() {
        lvTopplaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                DiaDanh diaDanh = diaDanhs.get(i);
                intent.putExtra("Name", diaDanh.getNameDiaDanh());
                intent.putExtra("City", diaDanh.getCity());
                intent.putExtra("Image_INT", diaDanh.getImage_int());
                intent.putExtra("Image", diaDanh.getImDiaDanh());
                intent.putExtra("Favourite", diaDanh.getFavotite());
                startActivity(intent);
            }
        });
    }

    private void linkView() {

        lvTopplaces = findViewById(R.id.lvTopplaces);
    }

    private void loadData() {

        diaDanhs = db.getTopDiaDanh();
        diaDanhAdapter = new DiaDanhAdapter(MainActivity.this, R.layout.top_places_row_item, diaDanhs);
        lvTopplaces.setAdapter(diaDanhAdapter);
        setRecentRecycler(diaDanhList);
    }

    private void setRecentRecycler(List<DiaDanh> diaDanhList){
        diaDanhList = db.getFavorite();
        recentRecycler = findViewById(R.id.recent_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this, (ArrayList<DiaDanh>) diaDanhList);
        recentRecycler.setAdapter(recentsAdapter);

    }

}




