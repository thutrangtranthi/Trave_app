package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import adapter.AccountAdapter;
import models.User;

public class AccountActivity extends AppCompatActivity {

    ListView listView;
    Button btnThemAccount;

    ArrayList<User> list;
    AccountAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        linkViews();

        this.setTitle("Quản lý Account");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>(MainActivity.db.getUser());
        adapter = new AccountAdapter(this, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnThemAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, InsertAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void linkViews() {
        listView = findViewById(R.id.lv_Account);
        btnThemAccount = findViewById(R.id.btn_AccountAdd);
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