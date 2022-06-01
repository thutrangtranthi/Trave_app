package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InsertDiaDanhActivity extends AppCompatActivity {

    EditText edtName, edtImage, edtImageDetail1, edtImageDetail2, edtImageDetail3, edtImageDetail4, edtCity;
    Button btnThem, btnThoat;
    Databases db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_dia_danh);

        this.setTitle("Thêm địa danh");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Mapping();
        db = new Databases(this);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertDiaDanh();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsertDiaDanhActivity.this, AdminDiaDanhActivity.class));
            }
        });
    }

    private void Mapping() {
    }

    private void InsertDiaDanh() {
    }
}