package com.example.alesha.notification;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.Calendar;
import java.util.List;

public class ShowActivity extends AppCompatActivity {

    DBHelper db;
    ListView lvMain;

    SQLiteDatabase sqLDB;
    SimpleCursorAdapter sCa;
    public static final String KEY_ID = "id_position";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        db = new DBHelper(this);
        sqLDB = db.getReadableDatabase();


        lvMain = (ListView) findViewById(R.id.lvMain);

        Cursor cr = sqLDB.query(DBHelper.DB_TABLE, null, null, null, null, null, null );

        sCa = new SimpleCursorAdapter(this, R.layout.item, cr, new String[] {DBHelper.NAME_COLUMN},
                new int[] {R.id.text1});

        lvMain.setAdapter(sCa);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShowActivity.this, FullActivity.class);
                intent.putExtra(KEY_ID, position);
                startActivity(intent);
            }
        });

        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                sqLDB.delete(DBHelper.DB_TABLE, "_id = " + position+1, null);
                return false;
            }
        });


    }
}
