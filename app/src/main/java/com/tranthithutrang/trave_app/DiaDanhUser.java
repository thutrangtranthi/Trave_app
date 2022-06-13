package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import adapter.DiaDanhAdapter;
import models.DiaDanh;

public class DiaDanhUser extends AppCompatActivity {

    public  static ArrayList<DiaDanh> diaDanhs;
    DiaDanhAdapter diaDanhAdapter;
    ListView lvDiaDanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_danh_user);

        this.setTitle("Danh sách địa danh");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linkView();
        loadData();
        addEvent();

    }

    private void addEvent() {
        lvDiaDanh.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DiaDanhUser.this, DetailsActivity.class);
                DiaDanh diaDanh = diaDanhs.get(i);
                intent.putExtra("Image", diaDanh.getImDiaDanh());
                intent.putExtra("ID", diaDanh.getIdDiaDanh());
                intent.putExtra("Name", diaDanh.getNameDiaDanh());
                intent.putExtra("City", diaDanh.getCity());
                intent.putExtra("Image_INT", diaDanh.getImage_int());
                intent.putExtra("Favourite", diaDanh.getFavotite());
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        diaDanhs = MainActivity.db.getDiaDanh();
        diaDanhAdapter = new DiaDanhAdapter(DiaDanhUser.this, R.layout.top_places_row_item, diaDanhs);
        lvDiaDanh.setAdapter(diaDanhAdapter);
    }

    private void linkView() {
        lvDiaDanh = findViewById(R.id.lvDiaDanhUser);
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