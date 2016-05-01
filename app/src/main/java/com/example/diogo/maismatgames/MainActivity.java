package com.example.diogo.maismatgames;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

final public class MainActivity extends AppCompatActivity {

    public boolean checkertabs;
    public boolean checkerModos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        final AlertDialog.Builder Erro = new AlertDialog.Builder(MainActivity.this);
        Erro.setTitle("Ups!, Algo está errado, verifique as suas configurações");
        InGame.sharedPreferences = getSharedPreferences(Configuracoes.MyPrefs, Context.MODE_PRIVATE);

        for (int i = 0; i <= 9; i++) {
            checkertabs = InGame.sharedPreferences.getBoolean("check" + i, false);
            if (checkertabs == true)
                break;
            else
               checkertabs = false;
        }

        for (int j = 10; j <= 12; j++)
        {
            checkerModos = InGame.sharedPreferences.getBoolean("check" + j, false);
            if (checkerModos == true)
                break;
            else
                checkerModos = false;

        }

        if(checkertabs == false || checkerModos == false ){
            Erro.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent myIntent = new Intent(getApplicationContext(), Configuracoes.class);
                    startActivity(myIntent);
                }
            });

            Erro.show();

        }
        else {
            Intent myintent = new Intent(getApplicationContext(), InGame.class);
            for (int j = 0; j <= 12; j++) {
                myintent.putExtra("CheckBox" + j, InGame.sharedPreferences.getBoolean("check" + j, false));
            }

            myintent.putExtra("spinner1", InGame.sharedPreferences.getString("spinnerValue" + 1, ""));
            myintent.putExtra("spinner2", InGame.sharedPreferences.getString("spinnerValue" + 2, ""));

            startActivity(myintent);
        }
    }


    public void showConfig(View view) {
        Intent myIntent = new Intent(getApplicationContext(), Configuracoes.class);
        startActivity(myIntent);

    }
    public void showRegras(View view) {
        Intent myIntent = new Intent(getApplicationContext(), Regras.class);
        startActivity(myIntent);

    }
}
