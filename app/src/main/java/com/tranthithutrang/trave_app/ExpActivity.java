package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import adapter.ExpAdapter;
import models.Experience;

public class ExpActivity extends AppCompatActivity {
    ArrayList<Experience> list;
    ExpAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp);


        this.setTitle("Kinh nghiệm du lịch");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();
        addEvent();
    }

    private void addEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ExpActivity.this, DetailExpActivity.class);
                Experience exp = list.get(i);
                intent.putExtra("name", exp.getName());
                intent.putExtra("id", exp.getId());
                intent.putExtra("image", exp.getImage());
                intent.putExtra("detail", exp.getDetail());
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        listView = (ListView) findViewById(R.id.lvExperience);
        list = new ArrayList<>();
        list = MainActivity.db.getExperience();
        adapter = new ExpAdapter(this, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}