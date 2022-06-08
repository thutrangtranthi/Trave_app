package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import models.Group;
import models.User;

public class UpdateAccountActivity extends AppCompatActivity {

    EditText edtAccountName, edtAccountEmail, edtAccountPhone, edtAccountPassword;
    Button btnAccountChange;
    Spinner spnUpdateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        this.setTitle("Sửa thông tin Account");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linkViews();

        Intent intent = getIntent();
        GetAccount(intent.getStringExtra("Username"));
    }


    private void linkViews() {
        edtAccountName = findViewById(R.id.edtthemAccountUserName);
        edtAccountEmail = findViewById(R.id.edtthemAccountEmail);
        edtAccountPhone = findViewById(R.id.edtthemAccountPhone);
        edtAccountPassword = findViewById(R.id.edtthemAccountPassword);
        btnAccountChange = findViewById(R.id.btnthemAccountChange);
        spnUpdateUser = findViewById(R.id.spnUpdateUser);
        ArrayList<Group> group = MainActivity.db.getGroup();
        ArrayList<String> groupName = new ArrayList<>();
        for (int i =0; i < group.size(); i++){
            groupName.add(group.get(i).getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,groupName);
        spnUpdateUser.setAdapter(adapter);
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
            int result = MainActivity.db.editUser(name, username, password, email, phone, id_group);
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
        User user = MainActivity.db.getUserByUsername(username);
        edtAccountName.setText(user.getName());
        edtAccountEmail.setText(user.getEmail());
        edtAccountPhone.setText(user.getPhone());
        edtAccountPassword.setText(user.getPassword());
        spnUpdateUser.setSelection(user.getIdGroup()-1);
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