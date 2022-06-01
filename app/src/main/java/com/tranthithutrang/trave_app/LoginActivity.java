package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import models.User;

public class LoginActivity extends AppCompatActivity {

    Button btnLoginLogin, btnLoginSignup;
    EditText edtLoginUsername, edtLoginPassword;
    Databases db;

    private void Mapping() {
        btnLoginLogin = (Button) findViewById(R.id.btnLoginLogin);
        btnLoginSignup = (Button) findViewById(R.id.btnLoginSignup);
        edtLoginUsername = (EditText) findViewById(R.id.edtLoginUsername);
        edtLoginPassword = (EditText) findViewById(R.id.edtLoginPassword);
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
                Bundle bundle = new Bundle();
                bundle.putString("Username", username);
                bundle.putString("NameOfUser", user.getName());
                bundle.putInt("IdGroup", user.getIdGroup());
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else {
                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Mapping();
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
}