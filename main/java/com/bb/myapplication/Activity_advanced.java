package com.bb.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

public class Activity_advanced extends AppCompatActivity {

    Button btBack;
    //Назначаем радиокнопкам значения по умолчанию
    Boolean sixbool = true;
    Boolean sevenbool = false;
    Boolean eightbool = false;
    private SharedPreferences prefsadv;
    //Поле ввода текста для заголовка
    private EditText hellotitletext;
    RadioGroup rdGroup;
    //Переменные для радиокнопок
    public RadioButton r1, r2, r3;
    //Переменные для передачи состояния из boolean в sharedPrefs
    String sixdata;
    String sevendata;
    String eightdata;
    Switch bgswitchvar;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        bgswitchvar = findViewById(R.id.bgswitch);
        prefsadv = getSharedPreferences("MY_DATA", MODE_PRIVATE);
        rdGroup = (RadioGroup)findViewById(R.id.radioGroup);
        //Поле заголовка
        String hellotitletext1 = prefsadv.getString("hellotitletext","");
        hellotitletext = (EditText) findViewById(R.id.hellotitletext);
        hellotitletext.setText(hellotitletext1);
        //Ассоциируем переменные с полями по id из xml
        r1 = findViewById(R.id.sixfields);
        r2 = findViewById(R.id.sevenfields);
        r3 = findViewById(R.id.eightfields);
        //При нажатии на радиокнопку, вызываем функцию Update с заданным ключом
        r1.setChecked(Update("rbsix"));
        r2.setChecked(Update("rbseven"));
        r3.setChecked(Update("rbeight"));

        //При нажатии первой кнопки добавляем True с ключом rbsix в RBDATA
        r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean r1_isChecked) {
                SaveIntoSharedPrefs("rbsix", r1_isChecked);
            }
        });
        //При нажатии второй кнопки добавляем True с ключом rbsix в RBDATA
        r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean r2_isChecked) {
                SaveIntoSharedPrefs("rbseven", r2_isChecked);
            }
        });
        //При нажатии третьей кнопки добавляем True с ключом rbsix в RBDATA
        r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean r3_isChecked) {
                SaveIntoSharedPrefs("rbeight", r3_isChecked);
            }
        });

        //Back button
        btBack = findViewById(R.id.btBackadvanced);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go back
                Intent intent = new Intent (
                        Activity_advanced.this,Activity_settings.class
                );
                startActivity(intent);
            }
        });
    }
    //Сохранение данных в SharedPreferences - ожидая ключ и значение булева типа
    private void SaveIntoSharedPrefs(String key, boolean value){
        SharedPreferences sp = getSharedPreferences("RBDATA",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    //Функция обновления значения в SharedPreferences
    private boolean Update(String key){
        SharedPreferences sp = getSharedPreferences("RBDATA",MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
    //Сохраняем данные по количеству полей
    public void SaveDataAdvanced(View view)
    {
        int checkedId = rdGroup.getCheckedRadioButtonId();
        if(checkedId == R.id.sixfields) {
            sixbool = true;
            sevenbool = Boolean.FALSE;
            eightbool = Boolean.FALSE;
        }
        else if (checkedId == R.id.sevenfields){
            sevenbool = true;
            sixbool = Boolean.FALSE;
            eightbool = Boolean.FALSE;
        }
        else if (checkedId == R.id.eightfields){
            eightbool = true;
            sevenbool = Boolean.FALSE;
            sixbool = Boolean.FALSE;
        }
        sixdata = String.valueOf(sixbool);
        sevendata = String.valueOf(sevenbool);
        eightdata = String.valueOf(eightbool);
        String hellofield = hellotitletext.getText().toString();
        SharedPreferences.Editor editor = prefsadv.edit();

        editor.putString("sixfields", sixdata);
        editor.putString("sevenfields", sevendata);
        editor.putString("eightfields", eightdata);
        editor.putString("hellotitletext", hellofield);
        editor.apply();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}