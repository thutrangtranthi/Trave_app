package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import models.User;

public class LoginActivity extends AppCompatActivity {

    Button btnLoginLogin, btnLoginSignup;
    EditText edtLoginUsername, edtLoginPassword;
    Databases db;

    ArrayList<User> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        linkView();

        db = new Databases(this);
        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        btnLoginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private void linkView() {
        btnLoginLogin = findViewById(R.id.btnLoginLogin);
        btnLoginSignup = findViewById(R.id.btnLoginSignup);
        edtLoginUsername = findViewById(R.id.edtLoginUsername);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
    }

    private void Login() {
        String username = edtLoginUsername.getText().toString().trim();
        String password = edtLoginPassword.getText().toString().trim();

        if (username == "" || password == "") {
            Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        else {
            db.openDataBase();
            int check = db.checkLogin(username, password);
            User user = db.getUserByUsername(username);
            if (check == 1) {
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("Name", user.getName());
                intent.putExtra("ID_Group", user.getIdGroup());
                startActivity(intent);
            }
            else {
                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

}