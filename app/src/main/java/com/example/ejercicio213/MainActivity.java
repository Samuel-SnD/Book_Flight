package com.example.ejercicio213;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<InformacionVuelo> info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = new ArrayList <InformacionVuelo>();
        EditText pas = findViewById(R.id.et5);
        ImageButton iv1 = findViewById(R.id.ib1);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(pas.getText().toString()) > 0 && Integer.parseInt(pas.getText().toString()) <= 19 ) {
                    int num = Integer.parseInt(pas.getText().toString()) - 1;
                    pas.setText(String.valueOf(num));
                }
            }
        });
        ImageButton iv2 = findViewById(R.id.ib2);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(pas.getText().toString()) >= 0 && Integer.parseInt(pas.getText().toString()) <= 18) {
                    int num = Integer.parseInt(pas.getText().toString()) + 1;
                    pas.setText(String.valueOf(num));
                }
            }
        });
        Button sf = findViewById(R.id.btn2);
        sf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = findViewById(R.id.rg1);
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);
                String selectedText;
                RadioButton rbb = findViewById(R.id.rb1);
                if (radioButtonID == rbb.getId()) {
                    selectedText = "Ida y Vuelta";
                }
                else
                    selectedText = "Ida";
                EditText et1 = findViewById(R.id.et1);
                String from = et1.getText().toString();
                EditText et2 = findViewById(R.id.et2);
                String to = et2.getText().toString();
                EditText et3 = findViewById(R.id.et3);
                String depart = et3.getText().toString();
                EditText et4 = findViewById(R.id.et4);
                String arrive = et4.getText().toString();
                EditText pas = findViewById(R.id.et5);
                int passengers = Integer.parseInt(pas.getText().toString());
                RadioGroup radioGroup2 = findViewById(R.id.rg2);
                int radioButtonID2 = radioGroup2.getCheckedRadioButtonId();
                RadioButton radioButton2 = (RadioButton) radioGroup2.findViewById(radioButtonID);
                RadioButton rb4 = findViewById(R.id.rb3);
                RadioButton rb5 = findViewById(R.id.rb4);
                String selectedText2;
                if (radioButtonID2 == rb4.getId()) {
                    selectedText2 = "NonStop";
                }
                else if (radioButtonID2 == rb5.getId())
                    selectedText2 = "One stop";
                else
                    selectedText2 = "2 or more";
                RadioButton rb = findViewById(R.id.rb2);
                TextView fin = findViewById(R.id.tvfin);
                if (radioButtonID == rb.getId()) {
                    InformacionVuelo vuelo = new InformacionVuelo(selectedText, from, to, depart, selectedText2 ,passengers);
                    info.add(vuelo);
                    fin.setText("Tipo de vuelo: " + selectedText + "\n From: " + from + "\n To:" + to + "\n Depart: " + depart + "\n Paradas: " + selectedText2 + "\n Número de pasageros: " + passengers);
                }
                else {
                    InformacionVuelo vuelocompleto = new InformacionVuelo(selectedText, from, to, depart, arrive, selectedText2, passengers);
                    info.add(vuelocompleto);
                    fin.setText("Tipo de vuelo: " + selectedText + "\n From: " + from + "\n To:" + to + "\n Depart: " + depart + "\n Paradas: " + selectedText2 + "\n Return: " + arrive +"\n Número de pasageros: " + passengers);
                }
            }
        });
        Button hs = findViewById (R.id.btn1);
        hs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView fin = findViewById(R.id.tvfin);
                fin.setText("");
                for (int i = 0; i < info.size(); i++) {
                    fin.append(info.get(i).toString() + "\n");
                }
            }
        });
    }
}