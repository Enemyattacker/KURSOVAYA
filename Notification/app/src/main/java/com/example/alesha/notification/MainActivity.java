package com.example.alesha.notification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button addB;
    private Button showB;
    private Button infoB;
    private Button appinfoB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addB = (Button) findViewById(R.id.add);
        showB = (Button) findViewById(R.id.show);
        infoB = (Button) findViewById(R.id.info);
        appinfoB = (Button) findViewById(R.id.appInfo);

        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intentAdd);
            }
        });

        showB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentShow = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(intentShow);
            }
        });

        infoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intentAdd);
            }
        });

        appinfoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(MainActivity.this, AppInfoActivity.class);
                startActivity(intentAdd);
            }
        });
    }

}