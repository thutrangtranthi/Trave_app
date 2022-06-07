package com.tranthithutrang.trave_app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.DiaDanhAdapter;
import adapter.RecentsAdapter;
import models.DiaDanh;


public class MainActivity extends AppCompatActivity {



    RecyclerView recentRecycler;
    RecentsAdapter recentsAdapter;


    ImageButton imageUser, imageExperience, imageLogout;
    ListView lvTopplaces;
    Button btnSeeAll;
    TextView txtUserName;
    DiaDanhAdapter diaDanhAdapter;
    ArrayList<DiaDanh> diaDanhList;
    public static boolean DEBUG_MODE =BuildConfig.BUILD_TYPE.equals("debug");

    public static ArrayList<DiaDanh> diaDanhs;
    public static Databases db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new Databases(this);
        db.openDataBase();


        linkView();
        loadData();
        addEvent();

        btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Intent intent2;
                if (intent.getIntExtra("ID_Group", 1) == 1){
                    intent2 = new Intent(MainActivity.this, DiaDanhUser.class);
                }else {
                    intent2 = new Intent(MainActivity.this, AdminDiaDanhActivity.class);
                }
                startActivity(intent2);
            }
        });

        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

        imageExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExpActivity.class);
                startActivity(intent);
            }
        });

        imageLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }

    private void addEvent() {
        lvTopplaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                DiaDanh diaDanh = diaDanhs.get(i);
                intent.putExtra("Name", diaDanh.getNameDiaDanh());
                intent.putExtra("ID", diaDanh.getIdDiaDanh());
                intent.putExtra("IDPT", diaDanh.getIdPT());
                intent.putExtra("City", diaDanh.getCity());
                intent.putExtra("Image_INT", diaDanh.getImage_int());
                intent.putExtra("Image", diaDanh.getImDiaDanh());
                intent.putExtra("Favourite", diaDanh.getFavotite());
                startActivity(intent);
            }
        });
    }

    private void linkView() {

        lvTopplaces = findViewById(R.id.lvTopplaces);
        btnSeeAll = findViewById(R.id.btnSeeAll);
        imageUser = findViewById(R.id.imgbUser);
        imageExperience = findViewById(R.id.imgbExp);
        imageLogout = findViewById(R.id.imgbLogout);
        txtUserName = findViewById(R.id.txtUserName);
    }

    private void loadData() {

        diaDanhs = db.getTopDiaDanh();
        diaDanhAdapter = new DiaDanhAdapter(MainActivity.this, R.layout.top_places_row_item, diaDanhs);
        lvTopplaces.setAdapter(diaDanhAdapter);
        setRecentRecycler(diaDanhList);
        Intent intent = getIntent();
        txtUserName.setText(intent.getStringExtra("Name"));
        if (intent.getIntExtra("ID_Group", 1) == 1){
            imageUser.setEnabled(false);
            Drawable buttonDrawable = imageUser.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
            //the color is a direct color int and not a color resource
            DrawableCompat.setTint(buttonDrawable, Color.GRAY);
            imageUser.setBackground(buttonDrawable);
        }else {
            imageUser.setEnabled(true);
        }
    }

    private void setRecentRecycler(List<DiaDanh> diaDanhList){
        diaDanhList = db.getFavorite();
        recentRecycler = findViewById(R.id.recent_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this, (ArrayList<DiaDanh>) diaDanhList);
        recentRecycler.setAdapter(recentsAdapter);

    }
}




