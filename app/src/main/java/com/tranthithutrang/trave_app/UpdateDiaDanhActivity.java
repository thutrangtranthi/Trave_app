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

import models.DiaDanh;
import models.Vehicle;

public class UpdateDiaDanhActivity extends AppCompatActivity {

    EditText edtName, edtImage, edtImageDetail1, edtImageDetail2,
            edtImageDetail3, edtCity;
    Button btnSua, btnThoat;
    Databases db;
    Spinner spnUpdateVehicle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dia_danh);

        linkView();

        this.setTitle("Sửa địa danh");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new Databases(this);
        ArrayList<Vehicle> vehicles = db.getVedicle();
        ArrayList<String> vehicleName = new ArrayList<>();
        for (int i =0; i < vehicles.size(); i++){
            vehicleName.add(vehicles.get(i).getName());
        }
        ArrayAdapter vehiclesAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,vehicleName);
        spnUpdateVehicle.setAdapter(vehiclesAdapter);
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 1);
        getDiaDanh(id);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                UpdateDiaDanh(id);
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateDiaDanhActivity.this, AdminDiaDanhActivity.class);
            }
        });

    }

    private void linkView() {
        btnSua = findViewById(R.id.btnUpdateDiaDanhSua);
        btnThoat = findViewById(R.id.btnUpdateDiaDanhThoat);
        edtName = findViewById(R.id.edtUpdateDiaDanhName);
        edtImage = findViewById(R.id.edtUpdateDiaDanhImage);
        edtImageDetail1 = findViewById(R.id.edtUpdateDiaDanhImageDetail1);
        edtImageDetail2 = findViewById(R.id.edtUpdateDiaDanhImageDetail2);
        edtImageDetail3 = findViewById(R.id.edtUpdateDiaDanhImageDetail3);
        edtCity = findViewById(R.id.edtUpdateDiaDanhCity);
        spnUpdateVehicle = findViewById(R.id.spnUpdateVehicle);
    }

    private void getDiaDanh(int id) {
        DiaDanh diaDanh = db.getDiaDanhById(id);
        edtName.setText(diaDanh.getNameDiaDanh());
        String image = diaDanh.getImage_int();
        String img = diaDanh.getImDiaDanh();
        //String[] img = diaDanh.getImDiaDanh().split(";");
        edtImage.setText(image);
        edtImageDetail1.setText(img);
//        edtImageDetail1.setText(img[0]);
//        edtImageDetail2.setText(img[1]);
//        edtImageDetail3.setText(img[2]);
        edtCity.setText(diaDanh.getCity());
        spnUpdateVehicle.setSelection(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void UpdateDiaDanh(int id) {
        String name = edtName.getText().toString().trim();
        String image = edtImage.getText().toString().trim();
        String imageDetail1 = edtImageDetail1.getText().toString().trim();
        String imageDetail2 = edtImageDetail2.getText().toString().trim();
        String imageDetail3 = edtImageDetail3.getText().toString().trim();
        String city = edtCity.getText().toString().trim();
        int id_pt = spnUpdateVehicle.getId();

        if (name == "" || image == "" || imageDetail1 == "" || imageDetail2 == "" ||
                imageDetail3 == "" || city == "") {
            Toast.makeText(UpdateDiaDanhActivity.this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            String img = String.join(";", imageDetail1, imageDetail2, imageDetail3);
            db.openDataBase();
            int result = db.editDiaDanh(id, name, image, img, city, 0, id_pt);
            if (result == 1) {
                Toast.makeText(UpdateDiaDanhActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateDiaDanhActivity.this, AdminDiaDanhActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(UpdateDiaDanhActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
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