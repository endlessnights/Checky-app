package com.bb.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Activity_settings extends AppCompatActivity {
    //Initialize Variable
    Button btBack;
    Button fcSubmit;
    Button btAdvanced;
    //Ассоциируем поля ввода с переменными с помощью массива
    EditText[] InputFields = new EditText[8];
    //Назначаем полям значения по умолчанию и сохраняем их в Intent
    String[] tsfield = new String[8];
    //Создаем массив элементов из XML по id
    public int[] list_of_fields = {
            R.id.inputField0,
            R.id.inputField1,
            R.id.inputField2,
            R.id.inputField3,
            R.id.inputField4,
            R.id.inputField5,
            R.id.inputField6,
            R.id.inputField7,
    };

    private SharedPreferences prefs;

    //Создание формы / открытие приложения
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Назначаем полям значения по умолчанию и сохраняем их в Intent
        prefs = getSharedPreferences("MY_DATA", MODE_PRIVATE);
        //Получаем данные по количеству используемых полей
        String sixfields = prefs.getString("sixfields", "true");
        String sevenfields = prefs.getString("sevenfields", "false");
        String eightfields = prefs.getString("eightfields", "false");
        EditText inputField71var = (EditText) findViewById(list_of_fields[6]);
        EditText inputField81var = (EditText) findViewById(list_of_fields[7]);
        if (sixfields.equals("true")){
            inputField71var.setVisibility(View.INVISIBLE);
            inputField81var.setVisibility(View.INVISIBLE);
        }
        else if (sevenfields.equals("true")) {
            inputField71var.setVisibility(View.VISIBLE);
            inputField81var.setVisibility(View.INVISIBLE);
        }
        else if (eightfields.equals("true")) {
            inputField71var.setVisibility(View.VISIBLE);
            inputField81var.setVisibility(View.VISIBLE);
        }
        tsfield[0] = prefs.getString("KEY_F0", "Выключил газ");
        tsfield[1] = prefs.getString("KEY_F1", "Выключил воду");
        tsfield[2] = prefs.getString("KEY_F2", "Покормил кошек");
        tsfield[3] = prefs.getString("KEY_F3", "Закрыл окна");
        tsfield[4] = prefs.getString("KEY_F4", "Выключил Интернет");
        tsfield[5] = prefs.getString("KEY_F5", "Закрыл дверь");
        tsfield[6] = prefs.getString("KEY_F6", "Выключил везде свет");
        tsfield[7] = prefs.getString("KEY_F7", "Вынес мусор");
        //Назначаем полям ввода текст из SharedPreferences
        for (int i=0; i<8; i++) {
            InputFields[i] = (EditText) findViewById(list_of_fields[i]);
            InputFields[i].setText(tsfield[i]);
        }

        //Создаем переменные для кнопок
        btBack = findViewById(R.id.bt_back);
        fcSubmit = findViewById(R.id.submit_fc);
        btAdvanced = findViewById(R.id.btAdvanced);

        //Кнопка Назад
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go back
                Intent intent = new Intent (
                        Activity_settings.this,MainActivity.class
                );
                startActivity(intent);
            }
        });
        //Кнопка Расширенные настройки/Дополнительно
        btAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Advanced Settings
                Intent intent = new Intent (
                        Activity_settings.this,Activity_advanced.class
                );
                startActivity(intent);
            }
        });
    }
    //Ссылка-значок на внешний ресурс - ссылка на мой телеграм
    public void tglink(View view){
        Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        myWebLink.setData(Uri.parse("https://t.me/EndlessNights"));
            startActivity(myWebLink);
    }

    //Кнопка Сохранить данные
    public void SaveData(View view)
    {
        for (int i=0; i<8;i++) {
            tsfield[i] = InputFields[i].getText().toString();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("KEY_F"+i, tsfield[i]);
            editor.apply();
        }
        // Открываем главную страницу
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}