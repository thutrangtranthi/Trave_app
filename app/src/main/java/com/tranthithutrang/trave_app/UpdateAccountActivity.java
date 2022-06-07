package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import models.User;

public class UpdateAccountActivity extends AppCompatActivity {

    EditText edtAccountName, edtAccountEmail, edtAccountPhone, edtAccountPassword;
    Button btnAccountChange;
    Databases db;
    Spinner spnUpdateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        this.setTitle("Sửa thông tin Account");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        linkViews();
        GetAccount(intent.getStringExtra("Username"));
    }


    private void linkViews() {
        edtAccountName = findViewById(R.id.edtthemAccountUserName);
        edtAccountEmail = findViewById(R.id.edtthemAccountName);
        edtAccountPhone = findViewById(R.id.edtthemAccountEmail);
        edtAccountPassword = findViewById(R.id.edtthemAccountPhone);
        btnAccountChange = findViewById(R.id.btnthemAccountChange);
        spnUpdateUser = findViewById(R.id.spnUpdateUser);
    }

    private void UpdateAccount(String username) {
        String name = edtAccountName.getText().toString().trim();
        String email = edtAccountEmail.getText().toString().trim();
        String phone = edtAccountPhone.getText().toString().trim();
        String password = edtAccountPassword.getText().toString().trim();
        int id_group = spnUpdateUser.getSelectedItemPosition();
        if (name == "" || email == "" || phone == "" || password == "") {
            Toast.makeText(this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        else {
            db.openDataBase();
            int result = db.editUser(name, username, password, email, phone, id_group);
            if (result == 1) {
                Toast.makeText(UpdateAccountActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateAccountActivity.this, AccountActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(UpdateAccountActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void GetAccount(String username) {
        db.openDataBase();
        User user = db.getUserByUsername(username);
        edtAccountName.setText(user.getName());
        edtAccountEmail.setText(user.getEmail());
        edtAccountPhone.setText(user.getPhone());
        edtAccountPassword.setText(user.getPassword());
        spnUpdateUser.setSelection(user.getIdGroup());
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