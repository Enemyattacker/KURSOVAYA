package com.example.alesha.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class FullActivity extends AppCompatActivity {

    public int sec, day, mes, god, hou, min;
    DBHelper db;
    public static final int NOTIFY_ID = 101;
    public NotificationManager notificationManager;
    public Notification notify;
    public Context context;
    SQLiteDatabase sqLDB;
    public Calendar c2;
    int id;
    TextView textName, textText, tiem, kek;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        db = new DBHelper(this);
        sqLDB = db.getReadableDatabase();
        Intent intent = getIntent();

        id = intent.getIntExtra(ShowActivity.KEY_ID, 0) + 1;


        textName = (TextView) findViewById(R.id.textName);
        textText = (TextView) findViewById(R.id.textText);
        tiem = (TextView) findViewById(R.id.tiem);
        kek = (TextView) findViewById(R.id.kek);


        Cursor cr = sqLDB.query(DBHelper.DB_TABLE, null, DBHelper.ID_COLUMN + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        cr.moveToFirst();
        int textik = cr.getColumnIndex("text");
        day = cr.getInt(cr.getColumnIndex("den"));
        mes = cr.getInt(cr.getColumnIndex("mes")) - 1;
        god = cr.getInt(cr.getColumnIndex("god"));
        hou = cr.getInt(cr.getColumnIndex("chas"));
        min = cr.getInt(cr.getColumnIndex("min"));
        sec = cr.getInt(cr.getColumnIndex("ms"));
        textName.setText("Напоминание");
        textText.setText(cr.getString(textik));

        c2 = new GregorianCalendar(god, mes, day, hou, min, sec);
        final Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT + 3"));

        long finalMillisec = c2.getTimeInMillis() - c.getTimeInMillis();

        kek.setText("Дата наступления " + day + "/" + mes + "/" + god + " " + hou + ":" + min);

        new CountDownTimer(finalMillisec, 1000) {
            public void onTick(long millsUntilFinished) {
                int daysNumber, hoursNumber, minsNumber, secsNumber;
                daysNumber = (int) Math.floor(millsUntilFinished/1000);
                secsNumber = daysNumber%60;
                daysNumber = (int) Math.floor(daysNumber/60);
                minsNumber = daysNumber%60;
                daysNumber = (int) Math.floor(daysNumber/60);
                hoursNumber = daysNumber%24;
                daysNumber = (int) Math.floor(daysNumber/24);
                tiem.setText("Осталось " + daysNumber + " дней " + hoursNumber + " часов " + minsNumber + " минут " + secsNumber + " секунд");
            }

            public void onFinish() {
                tiem.setText("Дата наступила");
                context = getApplicationContext();

                Intent notificationIntent = new Intent(context, ShowActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(context,
                        0, notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                Notification.Builder builder = new Notification.Builder(context);

                builder.setContentIntent(contentIntent)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setTicker("У вас напоминание")
                        .setAutoCancel(true)
                        .setContentTitle("Время напоминания пришло")
                        .setContentText("Нажмите, чтобы проверить");

                notify = builder.getNotification();

                notificationManager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(NOTIFY_ID, notify);


            }
        }.start();

        cr.close();


        db.close();
    }


}
