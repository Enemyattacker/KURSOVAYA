package com.example.alesha.notification;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class FullActivity extends AppCompatActivity {

    DBHelper db;
    //объект для подключения
    SQLiteDatabase sqLDB;
    int id;
    String selection;
    TextView tv, chasi;
    String TAG = "myTag";
    final long date = System.currentTimeMillis();
    long myMs, finalMS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        db = new DBHelper(this);
        sqLDB = db.getReadableDatabase()        ;
        Intent intent = getIntent();

        id = intent.getIntExtra(ShowActivity.KEY_ID, 0) + 1;
        //myMs = intent.getLongExtra("myMS", 0);

        //finalMS = myMs - date;
        Log.d(TAG, String.valueOf(id));

        tv= (TextView) findViewById(R.id.vivod);
        chasi= (TextView) findViewById(R.id.chasi);


        Cursor cr = sqLDB.query(DBHelper.DB_TABLE, null, DBHelper.ID_COLUMN + " = ?", new String[] { String.valueOf(id)}, null, null, "god, mes, den, chas, min" );

            cr.moveToFirst();
            int idshka = cr.getColumnIndex("_id");
            int imechka = cr.getColumnIndex("name");
            int textik = cr.getColumnIndex("text");
            tv.setText(cr.getLong(idshka) + cr.getString(imechka) + cr.getString(textik));

        /*new CountDownTimer(finalMS, 1000){
            public void onTick(long millisUntilFinished) {
                chasi.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                chasi.setText("done!");
            }
        }.start();
        */
        cr.close();



    }
}
