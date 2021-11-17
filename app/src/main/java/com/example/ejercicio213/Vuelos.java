package com.example.ejercicio213;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

public class Vuelos extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vuelos);

        InformacionVuelo vuelo = (InformacionVuelo) getIntent().getSerializableExtra("vuelo");
        InformacionVuelo vueloCompleto = (InformacionVuelo) getIntent().getSerializableExtra("vueloCompleto");


        if (vuelo != null) {
            String tipo = vuelo.getTipo();
            String from = vuelo.getFrom();
            String to = vuelo.getTo();
            String salida = vuelo.getDepart();
            int pasageros = vuelo.getPassengers();
            String trasbordos = vuelo.getNumparadas();
        } else {
            String tipo = vueloCompleto.getTipo();
            String from = vueloCompleto.getFrom();
            String to = vueloCompleto.getTo();
            String salida = vueloCompleto.getDepart();
            String llegada = vueloCompleto.getArrive();
            int pasageros = vueloCompleto.getPassengers();
            String trasbordos = vueloCompleto.getNumparadas();
        }


    }
}