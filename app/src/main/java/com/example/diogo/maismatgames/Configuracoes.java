package com.example.diogo.maismatgames;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.content.Intent;

import java.util.ArrayList;


final public class Configuracoes extends AppCompatActivity {
    public static CheckBox chk1 , chk2, chk3, chk4, chk5, chk6, chk7, chk8, chk9, chk10, chk11, chk12, chk13;
    public static Spinner spn1, spn2;
    public static String MyPrefs = "MyPrefs";



    public ArrayList<CheckBox> checkboxArray = new ArrayList<CheckBox>();






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);



        checkboxArray.add(chk1 = (CheckBox) findViewById(R.id.checkBox));
        checkboxArray.add(chk2 = (CheckBox) findViewById(R.id.checkBox2));
        checkboxArray.add(chk3 = (CheckBox) findViewById(R.id.checkBox3));
        checkboxArray.add(chk4 = (CheckBox) findViewById(R.id.checkBox4));
        checkboxArray.add(chk5 = (CheckBox) findViewById(R.id.checkBox5));                                //#calhamaços
        checkboxArray.add(chk6 = (CheckBox) findViewById(R.id.checkBox6));
        checkboxArray.add(chk7 = (CheckBox) findViewById(R.id.checkBox7));
        checkboxArray.add(chk8 = (CheckBox) findViewById(R.id.checkBox8));
        checkboxArray.add(chk9 = (CheckBox) findViewById(R.id.checkBox9));
        checkboxArray.add(chk10 = (CheckBox) findViewById(R.id.checkBox10));
        checkboxArray.add(chk11 = (CheckBox) findViewById(R.id.checkBoxModo1));
        checkboxArray.add(chk12 = (CheckBox) findViewById(R.id.checkBoxModo2));
        checkboxArray.add(chk13 = (CheckBox) findViewById(R.id.checkBoxModo3));
        spn1 = (Spinner)findViewById(R.id.spinner);
        spn2 = (Spinner)findViewById(R.id.spinner2);


    }

    @Override
    public void onPause() {
        super.onPause();
        for(int i = 0; i <= 12; i++)
            saveChecks(i,checkboxArray.get(i).isChecked());



        saveSpinner(spn1, 1);
        saveSpinner(spn2, 2);




    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i <= 12; i++)
            checkboxArray.get(i).setChecked(loadChecks(i));

        loadSpinner(spn1,1);
        loadSpinner(spn2,2);

    }

    private void saveChecks(int index, boolean isChecked) {



        SharedPreferences sharedPreferences = getSharedPreferences(MyPrefs,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putBoolean("check" + index, isChecked);

        editor.commit();
    }



    private void saveSpinner(Spinner SelectedItem, int identificador){


        SharedPreferences sharedPreferences = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int item = SelectedItem.getSelectedItemPosition();
        editor.putInt("spinnerSelection"+identificador, item);

        String valor = (String)SelectedItem.getSelectedItem();
        editor.putString("spinnerValue"+identificador,valor);

        editor.commit();
    }



    public void loadSpinner(Spinner loaded,int identificador){


        SharedPreferences sharedPreferences = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);




        loaded.setSelection(sharedPreferences.getInt("spinnerSelection" + identificador, 0));
    }
    public boolean loadChecks(int i) {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPrefs,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("check" + i, false);

    }

}


