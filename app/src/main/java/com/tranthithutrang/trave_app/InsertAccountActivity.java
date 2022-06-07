package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import models.Group;
import models.User;

public class InsertAccountActivity extends AppCompatActivity {

    EditText edtUserName, edtName, edtEmail, edtPhone, edtPassword;
    Button btnThem;
    Databases db;
    Spinner spnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_account);

        linkViews();
        addEvent();

        this.setTitle("Thêm Account");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addEvent() {
        db = MainActivity.db;
        ArrayList<Group> group = db.getGroup();
        ArrayList<String> groupName = new ArrayList<>();
        for (int i =0; i < group.size(); i++){
            groupName.add(group.get(i).getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,groupName);
        spnUser.setAdapter(adapter);
        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        GetAccount(username);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertAccount(username);
            }
        });
    }

    private void GetAccount(String username) {
        User user = db.getUserByUsername(username);
        edtUserName.setText(user.getUsername());
        edtName.setText(user.getName());
        edtEmail.setText(user.getEmail());
        edtPhone.setText(user.getPhone());
        edtPassword.setText(user.getPassword());
    }

    private void linkViews() {
        edtUserName = findViewById(R.id.edtthemAccountUserName);
        edtName = findViewById(R.id.edtthemAccountUserName);
        edtEmail = findViewById(R.id.edtthemAccountName);
        edtPhone = findViewById(R.id.edtthemAccountEmail);
        edtPassword = findViewById(R.id.edtthemAccountPhone);
        btnThem = findViewById(R.id.btnthemAccountChange);
        spnUser = findViewById(R.id.spnUser);
    }

        private void InsertAccount(String username) {
            String userName = edtUserName.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            int id_group = spnUser.getSelectedItemPosition();
            if (userName == "" || name == "" || email == "" || phone == "" || password == "") {
                Toast.makeText(this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
            else {
                db.openDataBase();
                int result = db.editUser(name, username, password, email, phone, id_group );
                if (result == 1) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
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