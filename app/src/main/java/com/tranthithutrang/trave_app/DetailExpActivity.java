package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import models.Experience;

public class DetailExpActivity extends AppCompatActivity {

    Experience experience;
    TextView tvExp;
    ImageView imExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_exp);

        linkView();
        loadData();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadData() {
        Intent intent = getIntent();
        experience = new Experience(
                intent.getIntExtra("id",0),
                intent.getStringExtra("name"),
                intent.getStringExtra("image"),
                intent.getStringExtra("detail"));
        this.setTitle(experience.getName());
        Glide.with(this)
                .load(experience.getImage())
                .error(R.drawable.ic_baseline_terrain_24)
                .skipMemoryCache(true)
                .into(imExp);
        tvExp.setText(Html.fromHtml(experience.getDetail()));
    }

    private void linkView() {
        tvExp = findViewById(R.id.tvExp);
        imExp = findViewById(R.id.imExp);
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