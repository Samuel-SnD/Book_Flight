package com.example.ejercicio213;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Vuelos extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    InformacionVuelo vueloFinal = new InformacionVuelo();
    ArrayList<InformacionVuelo> vuelos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vuelos);

        InformacionVuelo vuelo = (InformacionVuelo) getIntent().getSerializableExtra("Vuelo");
        Log.i("Vuelo", vuelo.toString());


        if (vuelo.getTipo().equalsIgnoreCase("Ida y Vuelta")) {
            db.collection("vuelos")
                    .whereEqualTo("Tipo", vueloFinal.getTipo())
                    .whereEqualTo("From", vueloFinal.getFrom())
                    .whereEqualTo("To", vueloFinal.getTo())
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
                                    vuelo.setPrecio(document.get("Precio").toString());
                                    vuelos.add(vuelo);
                                    ListView lvVuelos = (ListView)findViewById(R.id.lvvuelos);
                                    ListAdapter lAdapter = new ListAdapter(getApplicationContext(), vuelos);
                                    lvVuelos.setAdapter(lAdapter);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No se ha podido establecer una conexión con la base de datos",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            db.collection("vuelos")
                    .whereEqualTo("Tipo", vueloFinal.getTipo())
                    .whereEqualTo("From", vueloFinal.getFrom())
                    .whereEqualTo("To", vueloFinal.getTo())
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
                                    vuelo.setPrecio(document.get("Precio").toString());
                                    vuelos.add(vuelo);
                                }
                                ListView lvVuelos = (ListView)findViewById(R.id.lvvuelos);
                                ListAdapter lAdapter = new ListAdapter(getApplicationContext(), vuelos);
                                lvVuelos.setAdapter(lAdapter);
                            } else {
                                Toast.makeText(getApplicationContext(), "No se ha podido establecer una conexión con la base de datos",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }



    }
}