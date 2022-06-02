package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    ImageButton btnFavouite;
    ImageView imgAnhChinh,imgAnhPhu1,imgAnhPhu2,imgAnhPhu3, imgBack;
    TextView txtCity,txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        linkViews();
        initActivity();
    }

    private void initActivity() {
        Intent intent = getIntent();
        txtCity.setText(intent.getStringExtra("City"));
        txtName.setText(intent.getStringExtra("Name"));
        Glide.with(this)
                .load(intent.getStringExtra("Image_INT"))
                .error(R.drawable.ic_baseline_terrain_24)
                .skipMemoryCache(true)
                .into(imgAnhChinh);
        String[] array_image = intent.getStringExtra("Image").split(";");
        Glide.with(this)
                .load(array_image[0])
                .error(R.drawable.ic_baseline_terrain_24)
                .skipMemoryCache(true)
                .into(imgAnhPhu1);
        Glide.with(this)
                    .load(array_image[1])
                    .error(R.drawable.ic_baseline_terrain_24)
                    .skipMemoryCache(true)
                    .into(imgAnhPhu2);
        Glide.with(this)
                    .load(array_image[2])
                    .error(R.drawable.ic_baseline_terrain_24)
                    .skipMemoryCache(true)
                    .into(imgAnhPhu3);
        if(intent.getIntExtra("Favourite", 0) == 1)
            btnFavouite.setImageDrawable(getResources().getDrawable( R.drawable.ic_baseline_like_24_blue));
        }

    private void linkViews() {
        imgAnhChinh = findViewById(R.id.imgAnhChinh);
        imgAnhPhu1 = findViewById(R.id.imgAnhPhu1);
        imgAnhPhu2 = findViewById(R.id.imgAnhPhu2);
        imgAnhPhu3 = findViewById(R.id.imgAnhPhu3);
        imgBack = findViewById(R.id.imgBack);
        txtCity = findViewById(R.id.txtCity);
        txtName = findViewById(R.id.txtName);
        btnFavouite = findViewById(R.id.btnFavouite);
    }

    public void goback(View view) {
        finish();
    }
}