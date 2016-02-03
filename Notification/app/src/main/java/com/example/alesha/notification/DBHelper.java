package com.example.alesha.notification;


import android.content.Context; import android.database.Cursor; import android.database.sqlite.SQLiteDatabase; import android.database.sqlite.SQLiteDatabase.CursorFactory; import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


/**
 * Created by Alesha on 20.01.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "notification.db";
    public static final String DB_TABLE = "notificationTable";

    public static final String TEXT_COLUMN = "text";
    public static final String NAME_COLUMN = "name";
    public static final String ID_COLUMN = "_id";
    public static final String DAY_COLUMN = "den";
    public static final String MONTH_COLUMN = "mes";
    public static final String YEAR_COLUMN = "god";
    public static final String MIN_COLUMN = "min";
    public static final String HOUR_COLUMN = "chas";
    public static final String MILLISECONDS = "ms";


    //создаю бд
    private static final String DB_CREATE = "create table "
            + DB_TABLE + "(" + ID_COLUMN
            + " integer primary key autoincrement," +  NAME_COLUMN + " text not null," + TEXT_COLUMN + " text not null,"
            + DAY_COLUMN + " long," + MONTH_COLUMN + " long," + YEAR_COLUMN + " long,"
            + HOUR_COLUMN + " long, " + MIN_COLUMN + " long, " + MILLISECONDS + " long);";


    //тут конструктор
    public DBHelper(Context context){
        super(context, DB_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
