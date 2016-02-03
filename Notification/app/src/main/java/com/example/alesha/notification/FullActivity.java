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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

public class FullActivity extends AppCompatActivity {

    public int sec, day, mes, god, hou, min;
    DBHelper db;
    public static final int NOTIFY_ID = 101;
    public NotificationManager notificationManager;
    public Notification notify;
    public Context context;
    //объект для подключения
    SQLiteDatabase sqLDB;
    public Calendar c2;
    int id;
    private Timer myTimer;
    private TimerTask myTimerTask;
    TextView textName, textText, tiem;
    String TAG = "myTag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        db = new DBHelper(this);
        sqLDB = db.getReadableDatabase()        ;
        Intent intent = getIntent();

        id = intent.getIntExtra(ShowActivity.KEY_ID, 0) + 1;

        Log.d(TAG, String.valueOf(id));




        if (myTimer != null)
            myTimer.cancel();

        myTimer = new Timer();
        myTimerTask = new MyTimerTask();

        myTimer.schedule(myTimerTask, 1000, 1000);


        textName= (TextView) findViewById(R.id.textName);
        textText= (TextView) findViewById(R.id.textText);
        tiem = (TextView) findViewById(R.id.tiem);


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

        c2 = new GregorianCalendar(god, mes, day, hou, min, sec );

        cr.close();
        db.close();


    }

    class MyTimerTask extends TimerTask {
        public void run() {
            final Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT + 3"));
            final Calendar dis = new GregorianCalendar(TimeZone.getTimeZone("GMT + 3"));

            dis.setTime(new Date(c2.getTime().getTime() - c.getTime().getTime()));


            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    tiem.setText((dis.get(Calendar.DAY_OF_YEAR) - 1)+ " дней "  + (dis.get(Calendar.HOUR_OF_DAY)) + " часов" + (dis.get(Calendar.MINUTE) + " минут" + (dis.get(Calendar.SECOND) + " секунд ")));
                    if((dis.get(Calendar.DAY_OF_YEAR))==0&&(dis.get(Calendar.HOUR_OF_DAY))==0&&(dis.get(Calendar.MINUTE))==0&&(dis.get(Calendar.SECOND))==0){
                        myTimerTask.cancel();
                        myTimer.cancel();
                        context = getApplicationContext();

                        Intent notificationIntent = new Intent(context, MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(context,
                                0, notificationIntent,
                                PendingIntent.FLAG_CANCEL_CURRENT);

                        Notification.Builder builder = new Notification.Builder(context);

                        builder.setContentIntent(contentIntent)
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setTicker("У вас напоминание")
                                .setWhen(c2.getTimeInMillis())
                                .setAutoCancel(true)
                                .setContentTitle("Время напоминание пришло")
                                .setContentText("Нажмите, чтобы проверить");

                        notify = builder.build();

                        notificationManager = (NotificationManager) context
                                .getSystemService(Context.NOTIFICATION_SERVICE);

                        notificationManager.notify(NOTIFY_ID, notify);
                    }
                }
            });
        }
    }
}
