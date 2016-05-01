package com.example.diogo.maismatgames;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.lang.Integer;
import java.util.List;
import java.util.Random;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


final public class InGame extends AppCompatActivity {


//    public CheckBox chk1 , chk2, chk3, chk4, chk5, chk6, chk7, chk8, chk9, chk10, chk11, chk12, chk13;

    //Declaraçoes
    ProgressBar pbar;
    int nPerguntas;
    CountDownTimer timer;
    int progress = 0;
    int rModo;
    int rTabs;
    int ContadorDePerguntas = 1;
    Random r = new Random();
    public EditText campo1;
    public EditText campo2;
    public EditText campo3;
    public int ValorEmFalta;
    public int Resultado;
    public int NumErradas;
    public ArrayList<String> Corrigidas = new ArrayList<String>();
    public String erradas ="\n";





    //Arrays
    public static SharedPreferences sharedPreferences;
    ArrayList<Boolean> valCheckboxes = new ArrayList<Boolean>();
    ArrayList<Integer> tabsCheckados = new ArrayList<Integer>();
    ArrayList<Integer> modosCheckados = new ArrayList<Integer>();


    //Oncreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        final AlertDialog.Builder Finalizador = new AlertDialog.Builder(InGame.this);

        //Inicialização dos campos
        campo1 = (EditText) findViewById(R.id.editCampo1);
        campo2 = (EditText) findViewById(R.id.editCampo2);
        campo3 = (EditText) findViewById(R.id.editCampo3);

        // Intent
        Intent intent = getIntent();


        //Campos
        String Campo1 = campo1.getText().toString();
        String Campo2 = campo2.getText().toString();
        String Campo3 = campo3.getText().toString();

        //Spinners
        final String tempo = intent.getStringExtra("spinner1");
        final String nPerguntas = intent.getStringExtra("spinner2");
        final Integer Tempo = Integer.parseInt(tempo);


        //Declarar os modos de jogo 0,1,2 e tabuadas 0,1,2,3,4,5,6,7,8,9
        for (int i = 0; i <= 12; i++) {
            valCheckboxes.add(intent.getBooleanExtra("CheckBox" + i, false));
        }
        for (int j = 0; j <= 9; j++) {
            if (valCheckboxes.get(j) == true)
                tabsCheckados.add(j + 1);
        }
        if (valCheckboxes.get(10) == true)
            modosCheckados.add(1);
        if (valCheckboxes.get(11) == true)
            modosCheckados.add(2);
        if (valCheckboxes.get(12) == true)
            modosCheckados.add(3);


        //todo Randomizar
        //switch (valCheckboxes)


        //Progress bar
        pbar = (ProgressBar) findViewById(R.id.progressBar);
        pbar.setMax(Tempo * 10);
        pbar.setProgress(1);


        //EscolhePergunta();
        timer = new CountDownTimer(Tempo * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (millisUntilFinished / 100);
                pbar.setProgress(progress);
            }

            @Override
            public void onFinish() {

                if(ContadorDePerguntas <= Integer.parseInt(nPerguntas)) {

                    EscolhePergunta();
                    Corrigidas.add(String.valueOf(ValorEmFalta) + "*" + String.valueOf(rTabs) + "=" + String.valueOf(Resultado));
                    ContadorDePerguntas++;
                    NumErradas++;
                    timer.start();
                }else{
                    Finalizador.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(myIntent);
                        }
                    });
                    Finalizador.setTitle("Acabou! Erraste: " + String.valueOf(NumErradas) + " em " + String.valueOf(nPerguntas));

                    for(int i = 0; i < Corrigidas.size(); i++)
                        erradas = erradas + Corrigidas.get(i)+"\n";
                    Finalizador.setMessage("Correção: " + erradas );
                    Finalizador.show();
                }
            }
        };
        timer.start();
        EscolhePergunta();





        //region Input Dialog Key Listeners
        campo1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER :
                            if (ContadorDePerguntas <= Integer.parseInt(nPerguntas)) {
                            verificar();


                            timer.start();
                        } else {
                            Finalizador.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(myIntent);
                                }
                            });
                            Finalizador.setTitle("Acabou! Erraste: " + String.valueOf(NumErradas) + " em " + String.valueOf(nPerguntas));


                            for (int i = 0; i < Corrigidas.size(); i++)
                                erradas = erradas + Corrigidas.get(i) + "\n";

                            Finalizador.setMessage("Correção: " + erradas);
                            Finalizador.show();
                            timer.cancel();

                        }
                            return true;
                        default: break;
                    }
                }

                return false;
            }
        });

        campo2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER :
                            if (ContadorDePerguntas <= Integer.parseInt(nPerguntas)) {
                                verificar();


                                timer.start();
                            } else {
                                Finalizador.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(myIntent);
                                    }
                                });
                                Finalizador.setTitle("Acabou! Erraste: " + String.valueOf(NumErradas) + " em " + String.valueOf(nPerguntas));


                                for (int i = 0; i < Corrigidas.size(); i++)
                                    erradas = erradas + Corrigidas.get(i) + "\n";

                                Finalizador.setMessage("Correção: " + erradas);
                                Finalizador.show();
                                timer.cancel();

                            }
                            return true;
                        default: break;
                    }
                }

                return false;
            }
        });

        campo3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER :
                            if (ContadorDePerguntas <= Integer.parseInt(nPerguntas)) {
                                verificar();


                                timer.start();
                            } else {
                                Finalizador.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(myIntent);
                                    }
                                });
                                Finalizador.setTitle("Acabou! Erraste: " + String.valueOf(NumErradas) + " em " + String.valueOf(nPerguntas));


                                for (int i = 0; i < Corrigidas.size(); i++)
                                    erradas = erradas + Corrigidas.get(i) + "\n";

                                Finalizador.setMessage("Correção: " + erradas);
                                Finalizador.show();
                                timer.cancel();

                            }
                            return true;
                        default: break;
                    }
                }

                return false;
            }
        });
        //endregion


    }


    private void EscolhePergunta() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        campo1.getText().clear();
        campo2.getText().clear();
        campo3.getText().clear();
        rModo = modosCheckados.get(r.nextInt(modosCheckados.size())); //no modoCheckados fomos obter um index que é equivalente ao modo de jogo; esse index é gerado segundo o size do ArrayList
        rTabs = tabsCheckados.get(r.nextInt(tabsCheckados.size()));
        ValorEmFalta = r.nextInt(11); // valor que vai ser multiplicado pelo numero da tabuada que o utilizador escolheu e que foi randomizado


        Resultado = rTabs * ValorEmFalta;


        switch (rModo) {
            case 1:
                rModo = 1;
                campo1.setFocusableInTouchMode(true);
                campo1.requestFocus();
                imm.showSoftInput(campo1, InputMethodManager.SHOW_IMPLICIT);
                campo2.setFocusable(false);
                campo3.setFocusable(false);
                campo2.setText(String.valueOf(rTabs));
                campo3.setText(String.valueOf(Resultado));
                break;
            case 2:
                rModo = 2;
                campo1.setFocusable(false);
                campo1.setText(String.valueOf(rTabs));
                campo2.setFocusableInTouchMode(true);
                campo2.requestFocus();
                imm.showSoftInput(campo2, InputMethodManager.SHOW_IMPLICIT);
                campo3.setFocusable(false);
                campo3.setText(String.valueOf(Resultado));
                break;
            case 3:
                rModo = 3;
                campo1.setFocusable(false);
                campo1.setText(String.valueOf(rTabs));
                campo2.setFocusable(false);
                campo2.setText(String.valueOf(ValorEmFalta));
                campo3.setFocusableInTouchMode(true);
                campo3.requestFocus();
                imm.showSoftInput(campo3, InputMethodManager.SHOW_IMPLICIT);
                break;

        }


    }

    private void verificar() {
        int verificado;
        //boolean verificador = false;
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(InGame.this);

// Setting Dialog Title
        alertDialog2.setTitle("ERRO");

// Setting Dialog Message
        alertDialog2.setMessage("Introduz um valor");

        if (rModo == 1 && campo1.getText().toString().equals("") || rModo == 2 && campo2.getText().toString().equals("") || rModo == 3 && campo3.getText().toString().equals("")) {
            alertDialog2.show();
        } else {
            switch (rModo) {
                case 1:
                    rModo = 1;
                    verificado = Integer.parseInt(campo1.getText().toString());


                    if (verificado * Integer.parseInt(campo2.getText().toString()) != Resultado){
                        NumErradas++;
                        Corrigidas.add(String.valueOf(ValorEmFalta) + "*" + String.valueOf(rTabs) + "=" + String.valueOf(Resultado));
                    }

                        break;

                case 2:
                    rModo = 2;
                    verificado = Integer.parseInt(campo2.getText().toString());
                    if (verificado * Integer.parseInt(campo1.getText().toString()) != Resultado) {
                        NumErradas++;
                        Corrigidas.add(String.valueOf(rTabs) + "*" + String.valueOf(ValorEmFalta) + "=" + String.valueOf(Resultado));
                    }

                    break;

                case 3:
                    rModo = 3;

                    verificado = Integer.parseInt(campo3.getText().toString());
                    if (verificado != Integer.parseInt(campo2.getText().toString()) * Integer.parseInt(campo1.getText().toString())) {
                        NumErradas++;
                        Corrigidas.add(  String.valueOf(Resultado) + "=" + String.valueOf(ValorEmFalta) + "*" + String.valueOf(rTabs));
                    }

                    break;
            }
            EscolhePergunta();
            ContadorDePerguntas++;
        }
    }
}

//todo progressBar counter, checkbox checker and rest of the game
