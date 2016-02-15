package com.example.alesha.notification;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    final int DIALOG_TIME = 1;
    final int DIALOG_DATE = 2;
    int myYear, myMonth, myDay, myMinute, myHour;
    public TextView  dateView, dateTView;
    public EditText editText, nameText;
    public Button button;
    String dateE, timeE;
    DBHelper db;
    SQLiteDatabase sQDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db= new DBHelper(this);
        sQDB = db.getReadableDatabase();

        dateView = (TextView) findViewById(R.id.dateView);
        dateTView = (TextView) findViewById(R.id.dateTView);
        editText = (EditText) findViewById(R.id.editText);
        nameText = (EditText) findViewById(R.id.nameText);



        final Calendar c = Calendar.getInstance();
        myYear = c.get(Calendar.YEAR);
        myMonth = c.get(Calendar.MONTH);
        myDay = c.get(Calendar.DAY_OF_MONTH);
        myHour = c.get(Calendar.HOUR_OF_DAY);
        myMinute = c.get(Calendar.MINUTE);




        button = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put(DBHelper.TEXT_COLUMN, editText.getText().toString());
                cv.put(DBHelper.NAME_COLUMN, nameText.getText().toString());
                cv.put(DBHelper.DAY_COLUMN, myDay);
                cv.put(DBHelper.MONTH_COLUMN, myMonth);
                cv.put(DBHelper.YEAR_COLUMN, myYear);
                cv.put(DBHelper.HOUR_COLUMN, myHour);
                cv.put(DBHelper.MIN_COLUMN, myMinute);
                cv.put(DBHelper.MILLISECONDS, c.get(Calendar.SECOND));
                sQDB.insert(DBHelper.DB_TABLE, null, cv);
                db.close();
                finish();
            }
        });

    }

    public void startDate(View view) { showDialog(DIALOG_DATE);}

    protected Dialog onCreateDialog(int id){
        switch(id) {
            case DIALOG_DATE:
                return new DatePickerDialog(this, callDate, myYear, myMonth, myDay);
            case DIALOG_TIME:
                return new TimePickerDialog(this, callTime, myHour, myMinute, true);
        }
        return null;

     }


    DatePickerDialog.OnDateSetListener callDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear+1;
            myDay = dayOfMonth;
            dateE= myDay+ " " + myMonth + " " + myYear;
            showDialog(DIALOG_TIME);
        }
    };

    TimePickerDialog.OnTimeSetListener callTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            timeE = pad(myHour) + " : " + pad(myMinute);
            dateView.setText(dateE + " " + timeE);
        }
    };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}
