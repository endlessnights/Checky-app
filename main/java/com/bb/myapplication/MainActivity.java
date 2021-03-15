package com.bb.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //Создаем 9 переключателей с помощью массива
    SwitchCompat[] switcharray = new SwitchCompat[9];
    Boolean Reset; //Булев для выключения переключателя
    Button NextButton;
    public int[] list_of_switches = {
            R.id.switch_compat1,
            R.id.switch_compat2,
            R.id.switch_compat3,
            R.id.switch_compat4,
            R.id.switch_compat5,
            R.id.switch_compat6,
            R.id.switch_compat7,  //6
            R.id.switch_compat8,  //7
            R.id.switch_compat10, //переключатель "Вы уверены?" //8
    };
    //Нажатие кнопки Сброс
    public void ResetButtonClick (View view) throws IllegalAccessException {
        Reset =false;
        if (switcharray[8].isChecked()) { //Если переключатель "Вы уверены?" нажат, то разрешаем переключить в false остальные переключатели
            SharedPreferences.Editor editor = getSharedPreferences("save"
                    ,MODE_PRIVATE).edit();
            //Сохраняем в Intent значения всех переключателей в False
            for (int k=0; k<10; k++) {
                editor.putBoolean("value"+k, false);
            }
            editor.apply();
            //Устанавливаем все переключатели в значение False
            for (int i=0;i<9;i++){
                switcharray[i].setChecked(false);
            }
            //Reset background color of checked SwitchCompats
            for (int i = 0; i < 9; i++) {
                findViewById(list_of_switches[i]).setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    //Создание формы / открытие приложения
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Назначаем полям значения по умолчанию и сохраняем их в Intent
        String[] tsfield = new String[8];
        SharedPreferences prefs = getSharedPreferences("MY_DATA", MODE_PRIVATE);
        tsfield[0] = prefs.getString("KEY_F0", "Выключил газ");
        tsfield[1] = prefs.getString("KEY_F1", "Выключил воду");
        tsfield[2] = prefs.getString("KEY_F2", "Покормил кошек");
        tsfield[3] = prefs.getString("KEY_F3", "Закрыл окна");
        tsfield[4] = prefs.getString("KEY_F4", "Выключил Интернет");
        tsfield[5] = prefs.getString("KEY_F5", "Закрыл дверь");
        tsfield[6] = prefs.getString("KEY_F6", "Выключил везде свет");
        tsfield[7] = prefs.getString("KEY_F7", "Вынес мусор");
        //Получаем настройки текста заголовка
        String hellotext = prefs.getString("hellotitletext", "");
        switcharray[6] = findViewById(list_of_switches[6]);
        switcharray[7] = findViewById(list_of_switches[7]);
        //Получаем настройки количества полей
        String sixfields = prefs.getString("sixfields", "true");
        String sevenfields = prefs.getString("sevenfields", "false");
        String eightfields = prefs.getString("eightfields", "false");

        if (sixfields.equals("true")){
            switcharray[6].setVisibility(View.GONE);
            switcharray[7].setVisibility(View.GONE);
        }
        else if (sevenfields.equals("true")) {
            switcharray[6].setVisibility(View.VISIBLE);
            switcharray[7].setVisibility(View.GONE);
        }
        else if (eightfields.equals("true")) {
            switcharray[6].setVisibility(View.VISIBLE);
            switcharray[7].setVisibility(View.VISIBLE);
        }
        //Создаем массив из TextView
        TextView[] textarr = new TextView[8];
        //Каждому переключателю назначаем текст из итерации поля tsfield
        for (int i=0; i<8;i++){
            textarr[i] = (TextView) findViewById(list_of_switches[i]);
            textarr[i].setText(tsfield[i]);
        }
        //Назначаем текст заголовка
            TextView textView5 = (TextView) findViewById(R.id.textView5);
            textView5.setText(hellotext);
        //Отображать заголовок, если соотв. поле заполнено
        if(!hellotext.matches(""))
        {
            textView5.setVisibility(View.VISIBLE);
        }

        //Создаем связь каждого элемента переключателя по id из XML с соответствующей переменной типа SwitchCompat
        for (int i=0;i<9;i++) {
            switcharray[i] = findViewById(list_of_switches[i]);
        }
        //Создаем связь кнопки по id bt_next из xml переменной NextButton
        NextButton = findViewById(R.id.bt_next);

        //Используем SharedPreferences = "save"
        SharedPreferences sharedPreferences = getSharedPreferences("save"
                , MODE_PRIVATE);
        //При первом запуске - все переключатели в False
        for (int k=0; k<9; k++) {
        switcharray[k].setChecked(sharedPreferences.getBoolean("value"+k, false));
        }
        //При переключении переключателей сохраняем данные, а также, проверяем их при повторном запуске
        for (int k=0; k<9; k++) {
            final int finalK = k;
            switcharray[k].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (switcharray[finalK].isChecked()) {
                        //когда переключатель включен
                        Reset = true; //
                        switcharray[finalK].setBackgroundColor(Color.parseColor("#c8a2c6"));
                        SharedPreferences.Editor editor = getSharedPreferences("save"
                                , MODE_PRIVATE).edit();
                        editor.putBoolean("value" + finalK, true);
                        editor.apply();
                        switcharray[finalK].setChecked(true);
                    } else {
                        //когда переключатель выключен
                        SharedPreferences.Editor editor = getSharedPreferences("save"
                                , MODE_PRIVATE).edit();
                        editor.putBoolean("value" + finalK, false);
                        Reset = false;
                        switcharray[finalK].setBackgroundColor(Color.TRANSPARENT);
                        editor.apply();
                        switcharray[finalK].setChecked(false);
                    }
                }
            });
        }
        //Кнопка открытия страницы настроек
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to next activity
                Intent intent2 = new Intent(MainActivity.this, Activity_settings.class);
                startActivity(intent2);
            }
        });
    }
}
