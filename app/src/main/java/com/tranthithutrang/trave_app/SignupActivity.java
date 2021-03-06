package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText edtSignupName, edtSignupPhone, edtSignupEmail, edtSignupUsername, edtSignupPassword, edtSignupConfirmPassword;
    Button btnSignupSignup, btnSignupExit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_avtivity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        linkView();

        btnSignupSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup();
            }
        });
        btnSignupExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void linkView() {
        edtSignupName = findViewById(R.id.edtSignupName);
        edtSignupPhone = findViewById(R.id.edtSignupPhone);
        edtSignupEmail = findViewById(R.id.edtSignupEmail);
        edtSignupUsername = findViewById(R.id.edtSignupUsername);
        edtSignupPassword = findViewById(R.id.edtSignupPassword);
        edtSignupConfirmPassword = findViewById(R.id.edtSignupConfirmPassword);
        btnSignupSignup = findViewById(R.id.btnSignupSignup);
        btnSignupExit = findViewById(R.id.btnSignupExit);
    }

    private void Signup(){
        if (edtSignupUsername.getText().toString() == "" || edtSignupPassword.getText().toString() == "" || edtSignupName.getText().toString() == "" || edtSignupPhone.getText().toString() == "" || edtSignupEmail.getText().toString() == "" || edtSignupConfirmPassword.getText().toString() == ""){

            Toast.makeText(SignupActivity.this, "Vui l??ng ??i???n ?????y ????? th??ng tin", Toast.LENGTH_SHORT).show();

        }
        else {
            String username = edtSignupUsername.getText().toString().trim();
            String password = edtSignupPassword.getText().toString().trim();
            String name = edtSignupName.getText().toString().trim();
            String phone = edtSignupPhone.getText().toString().trim();
            String email = edtSignupEmail.getText().toString().trim();
            String confirmPassword = edtSignupConfirmPassword.getText().toString().trim();


            if (PhoneUp(phone)){
                if (password.equals(confirmPassword)) {
                    int check = MainActivity.db.checkUsername(username);
                    if (check == 0) {
                        int insert = MainActivity.db.addUser(name, username, password, email, phone, 1);
                        if (insert == 1) {
                            Toast.makeText(SignupActivity.this, "????ng k?? th??nh c??ng", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        }
                        else {
                            Toast.makeText(SignupActivity.this, "????ng k?? th???t b???i", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(SignupActivity.this, "T??n ????ng nh???p ???? t???n t???i", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SignupActivity.this, "M???t kh???u kh??ng kh???p", Toast.LENGTH_SHORT).show();
                    edtSignupPassword.setText("");
                    edtSignupConfirmPassword.setText("");
                }
            }else {
                Toast.makeText(SignupActivity.this, "S??? ??i???n tho???i kh??ng h???p l???", Toast.LENGTH_SHORT).show();
                edtSignupPhone.setText("");
            }
        }
    }
    private boolean PhoneUp(String SDT){
        if(SDT.length() == 10){
            return true;
        }else {
            return false;
        }

    }

}