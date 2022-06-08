package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import models.Vehicle;

public class InsertDiaDanhActivity extends AppCompatActivity {

    EditText edtName, edtImage, edtImageDetail1, edtImageDetail2, edtImageDetail3, edtCity;
    Button btnThem, btnThoat;
    Spinner spnInsertVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_dia_danh);

        this.setTitle("Thêm địa danh");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linkView();
        loadData();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    InsertDiaDanh();
                }
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsertDiaDanhActivity.this, AdminDiaDanhActivity.class));
            }
        });
    }

    private void loadData() {
        ArrayList<Vehicle> vehicles = MainActivity.db.getVedicle();
        ArrayList<String> vehicleName = new ArrayList<>();
        for (int i =0; i < vehicles.size(); i++){
            vehicleName.add(vehicles.get(i).getName());
        }
        ArrayAdapter vehiclesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,vehicleName);
        spnInsertVehicle.setAdapter(vehiclesAdapter);
    }

    private void linkView() {
        btnThem = findViewById(R.id.btnInsertDiaDanhThem);
        btnThoat = findViewById(R.id.btnInsertDiaDanhThoat);
        edtName =findViewById(R.id.edtInsertDiaDanhName);
        edtImage = findViewById(R.id.edtInsertDiaDanhImage);
        edtImageDetail1 = findViewById(R.id.edtInsertDiaDanhImageDetail1);
        edtImageDetail2 = findViewById(R.id.edtInsertDiaDanhImageDetail2);
        edtImageDetail3 = findViewById(R.id.edtInsertDiaDanhImageDetail3);
        edtCity = findViewById(R.id.edtInsertDiaDanhCity);
        spnInsertVehicle = findViewById(R.id.spnInsertVehicle);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void InsertDiaDanh() {
        String name = edtName.getText().toString().trim();
        String image = edtImage.getText().toString().trim();
        String imageDetail1 = edtImageDetail1.getText().toString().trim();
        String imageDetail2 = edtImageDetail2.getText().toString().trim();
        String imageDetail3 = edtImageDetail3.getText().toString().trim();
        String city = edtCity.getText().toString().trim();
        int id_pt = spnInsertVehicle.getId();
        String img = "";

        if (name == "" || image == "" || imageDetail1 == "" || imageDetail2 == "" ||
                imageDetail3 == ""  || city == ""){
            Toast.makeText(InsertDiaDanhActivity.this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else {
            img = String.join(";", imageDetail1, imageDetail2, imageDetail3);
        }
        int result = MainActivity.db.addDiaDanh(name, image, img, city, 0, id_pt);
        if (result == 1) {
            Toast.makeText(InsertDiaDanhActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(InsertDiaDanhActivity.this, AdminDiaDanhActivity.class));
        }
        else {
            Toast.makeText(InsertDiaDanhActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return true;
    }
}