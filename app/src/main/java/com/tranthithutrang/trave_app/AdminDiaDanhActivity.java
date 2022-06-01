package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import adapter.AdminDiaDanhAdapter;
import models.DiaDanh;

public class AdminDiaDanhActivity extends AppCompatActivity {

    ListView listView;
    Button btnThem;
    ArrayList<DiaDanh> list;
    AdminDiaDanhAdapter adapter;
    Databases db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dia_danh);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        this.setTitle("Quản lý địa danh");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new Databases(this);
        listView = (ListView) findViewById(R.id.lv_DiaDanh);
        btnThem = (Button) findViewById(R.id.btn_AdminDiaDanhAdd);
        list = new ArrayList<>();
        adapter = new AdminDiaDanhAdapter(this, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDiaDanhActivity.this, InsertDiaDanhActivity.class);
                startActivity(intent);
            }
        });
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