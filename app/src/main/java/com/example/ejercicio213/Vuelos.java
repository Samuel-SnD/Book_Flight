package com.example.ejercicio213;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Vuelos extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    InformacionVuelo vueloFinal = new InformacionVuelo();
    CollectionReference citiesRef = db.collection("vuelos");
    ArrayList <InformacionVuelo> vuelos;
    boolean completo;

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
            InformacionVuelo vueloFinal = new InformacionVuelo(tipo, from, to, salida, trasbordos, pasageros);
            completo = false;
        } else {
            String tipo = vueloCompleto.getTipo();
            String from = vueloCompleto.getFrom();
            String to = vueloCompleto.getTo();
            String salida = vueloCompleto.getDepart();
            String llegada = vueloCompleto.getArrive();
            int pasageros = vueloCompleto.getPassengers();
            String trasbordos = vueloCompleto.getNumparadas();
            InformacionVuelo vueloFinal = new InformacionVuelo(tipo, from, to, salida, llegada, trasbordos, pasageros);
            completo = true;
        }

        if (completo) {
        db.collection("vuelos")
                .whereEqualTo("Tipo", vueloFinal.getTipo().toString())
                .whereEqualTo("From", vueloFinal.getFrom().toString())
                .whereEqualTo("To", vueloFinal.getTo().toString())
                .whereEqualTo("Trasbordos", vueloFinal.getNumparadas())
                .whereEqualTo("Depart", vueloFinal.getDepart())
                .whereEqualTo("Arrive", vueloFinal.getArrive())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                InformacionVuelo vuelo = new InformacionVuelo();
                                vuelo.setTipo(document.get("Tipo").toString());
                                vuelo.setFrom(document.get("From").toString());
                                vuelo.setTo(document.get("To").toString());
                                vuelo.setDepart(document.get("Depart").toString());
                                vuelo.setArrive(document.get("Arrive").toString());
                                vuelo.setNumparadas(document.get("Trasbordos").toString());
                                vuelo.setPrecio(Float.parseFloat(document.get("Precio").toString()));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No se ha podido establecer una conexión con la base de datos",
                                    Toast.LENGTH_LONG);
                        }
                    }
                });
        } else {
            db.collection("vuelos")
                    .whereEqualTo("Tipo", vueloFinal.getTipo().toString())
                    .whereEqualTo("From", vueloFinal.getFrom().toString())
                    .whereEqualTo("To", vueloFinal.getTo().toString())
                    .whereEqualTo("Trasbordos", vueloFinal.getNumparadas())
                    .whereEqualTo("Depart", vueloFinal.getDepart())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    InformacionVuelo vuelo = new InformacionVuelo();
                                    vuelo.setTipo(document.get("Tipo").toString());
                                    vuelo.setFrom(document.get("From").toString());
                                    vuelo.setTo(document.get("To").toString());
                                    vuelo.setDepart(document.get("Depart").toString());
                                    vuelo.setNumparadas(document.get("Trasbordos").toString());
                                    vuelo.setPrecio(Float.parseFloat(document.get("Precio").toString()));
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No se ha podido establecer una conexión con la base de datos",
                                        Toast.LENGTH_LONG);
                            }
                        }
                    });
        }


    }
}