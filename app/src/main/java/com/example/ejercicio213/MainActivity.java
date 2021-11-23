package com.example.ejercicio213;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Intent it = new Intent(MainActivity.this, Vuelos.class);

                // Obtengo el tipo de vuelo
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

                //Obtengo la ciudad de salida
                EditText et1 = findViewById(R.id.et1);
                String from = et1.getText().toString();

                //Obtengo la ciudad de destino
                EditText et2 = findViewById(R.id.et2);
                String to = et2.getText().toString();

                //Obtengo la fecha de salida
                EditText et3 = findViewById(R.id.et3);
                String depart = et3.getText().toString();

                //Obtengo la fecha de llegada
                EditText et4 = findViewById(R.id.et4);
                String arrive = et4.getText().toString();

                //Obtengo el número de pasageros
                EditText pas = findViewById(R.id.et5);
                int passengers = Integer.parseInt(pas.getText().toString());

                //Obtengo el número de paradas
                RadioGroup radioGroup2 = findViewById(R.id.rg2);
                int radioButtonID2 = radioGroup2.getCheckedRadioButtonId();
                RadioButton radioButton2 = (RadioButton) radioGroup2.findViewById(radioButtonID);
                RadioButton rb4 = findViewById(R.id.rb3);
                RadioButton rb5 = findViewById(R.id.rb4);
                String selectedText2;
                if (radioButtonID2 == rb4.getId()) {
                    selectedText2 = "Ninguno";
                }
                else if (radioButtonID2 == rb5.getId())
                    selectedText2 = "Uno";
                else
                    selectedText2 = "Dos o mas";

                //Dependiendo del tipo del vuelo creo y añado el objeto al arraylist con uno u otro constructor
                RadioButton rb = findViewById(R.id.rb2);
                TextView fin = findViewById(R.id.tvfin);
                if (radioButtonID == rb.getId()) {
                    InformacionVuelo vuelo = new InformacionVuelo(selectedText, from, to, depart, selectedText2 ,passengers);
                    it.putExtra("Vuelo", vuelo);
                    Map <String, Object> vue = new HashMap <> ();
                    vue.put(Calendar.getInstance().getTime().toString(), Arrays.asList(vuelo.getTipo(), vuelo.getFrom(), vuelo.getTo(), vuelo.getNumparadas(), vuelo.getPassengers(), vuelo.getDepart()));
                    db.collection("historial").document(user.getEmail())
                            .set(vue, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error writing document", e);
                                }
                            });
                }
                else {
                    InformacionVuelo vuelo = new InformacionVuelo(selectedText, from, to, depart, arrive, selectedText2, passengers);
                    it.putExtra("Vuelo", vuelo);
                    Map <String, Object> vue = new HashMap <> ();
                    vue.put(Calendar.getInstance().getTime().toString(), Arrays.asList(vuelo.getTipo(), vuelo.getFrom(), vuelo.getTo(), vuelo.getNumparadas(), vuelo.getPassengers(), vuelo.getDepart(), vuelo.getArrive()));
                    db.collection("historial").document(user.getEmail())
                            .set(vue, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error writing document", e);
                                }
                            });
                }

                //Inicio la actividad con el intent que he creado
                startActivity(it);
            }
        });

        Button hs = findViewById (R.id.btn1);
        hs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, Historial.class);
                startActivity(it);
            }
        });
    }
}