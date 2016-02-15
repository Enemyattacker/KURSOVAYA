package com.example.alesha.notification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AppInfoActivity extends AppCompatActivity {

    TextView appinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        appinfo = (TextView) findViewById(R.id.appInfo);

        appinfo.setText("Приложение по организации трудового процесса. Предназначено для добавления, просмотра, удаления напоминаний. При наступлении даты напоминания выводится уведомление. Приложение разработано Стеценко Алексеем и Мешалкиным Сергеем. ");


    }
}
