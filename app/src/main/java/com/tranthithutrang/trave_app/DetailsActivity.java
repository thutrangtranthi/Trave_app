package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import models.DiaDanh;

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
        addEvent();
    }

    private void addEvent() {
        btnFavouite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
//                DiaDanh newDiaDanh = new DiaDanh(
//                        intent.getIntExtra("ID",0),
//                        intent.getStringExtra("Name"),
//                        intent.getStringExtra("Image"),
//                        intent.getStringExtra("Image_INT"),
//                        intent.getStringExtra("City"),
//                        intent.getIntExtra("Favorite",0),
//                        intent.getIntExtra("IDPT",0)
//                );
                DiaDanh newDiaDanh = MainActivity.db.getDiaDanhById( intent.getIntExtra("ID",0));
                if(MainActivity.db.getDiaDanhById(newDiaDanh.getIdDiaDanh()).getFavotite()==1){
                    MainActivity.db.editDiaDanh(newDiaDanh.getIdDiaDanh(),newDiaDanh.getNameDiaDanh(),newDiaDanh.getImDiaDanh(),newDiaDanh.getImage_int(),newDiaDanh.getCity(),0,newDiaDanh.getIdPT());
                    btnFavouite.setImageDrawable(getResources().getDrawable( R.drawable.ic_baseline_like_24));
                    Toast.makeText(DetailsActivity.this, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }else{
                    MainActivity.db.editDiaDanh(newDiaDanh.getIdDiaDanh(),newDiaDanh.getNameDiaDanh(),newDiaDanh.getImDiaDanh(),newDiaDanh.getImage_int(),newDiaDanh.getCity(),1,newDiaDanh.getIdPT());
                    btnFavouite.setImageDrawable(getResources().getDrawable( R.drawable.ic_baseline_like_24_blue));
                    Toast.makeText(DetailsActivity.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initActivity() {
        Intent intent = getIntent();
        DiaDanh newDiaDanh = MainActivity.db.getDiaDanhById( intent.getIntExtra("ID",0));
        txtCity.setText(newDiaDanh.getCity());
        txtName.setText(newDiaDanh.getNameDiaDanh());
        Glide.with(this)
                .load(newDiaDanh.getImage_int())
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
        if(newDiaDanh.getFavotite()== 1)
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
        Intent intent = getIntent();
        Intent intent1 = new Intent(DetailsActivity.this, MainActivity.class);
        intent1.putExtra("ID_Group", intent.getIntExtra("ID_Group",0));
        startActivity(intent1);
    }
}