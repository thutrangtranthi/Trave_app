package com.tranthithutrang.trave_app;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import adapter.ExpAdapter;
import models.Experience;

public class ExpActivity extends AppCompatActivity {
    ArrayList<Experience> list;
    ExpAdapter adapter;
    Databases db;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new Databases(this);
        this.setTitle("Kinh nghiệm du lịch");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.lvExperience);
        list = new ArrayList<>();
        list = db.getExperience();
        adapter = new ExpAdapter(this, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}