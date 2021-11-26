package com.example.ejercicio213;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Vuelos extends AppCompatActivity {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public int passengers;
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<InformacionVuelo> vuelos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vuelos);

        InformacionVuelo vuelo = (InformacionVuelo) getIntent().getSerializableExtra("Vuelo");
        passengers = getIntent().getIntExtra("Passengers", 0);

        // Compruebo si el vuelo es de Ida y Vuelta o solamente de Ida
        if (vuelo.getTipo().equalsIgnoreCase("Ida y Vuelta")) {
            // Recogo todos los vuelos de la base de datos que coincidan con los datos
            // que ha proporcionado el usuario.
            db.collection("vuelos")
                    .whereEqualTo("Tipo", vuelo.getTipo())
                    .whereEqualTo("From", vuelo.getFrom())
                    .whereEqualTo("To", vuelo.getTo())
                    .whereEqualTo("Trasbordos", vuelo.getNumparadas())
                    .whereEqualTo("Depart", vuelo.getDepart())
                    .whereEqualTo("Arrive", vuelo.getArrive())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            // Si se realiza correctamente voy vuelo a vuelo generándolos para
                            // añadirlos a un ArrayList y más adelante poder generar la vista
                            // con estos vuelos.
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
                                    ListView lvVuelos = (ListView) findViewById(R.id.lvvuelos);
                                    ListAdapter lAdapter = new ListAdapter(getApplicationContext(), vuelos);
                                    lvVuelos.setAdapter(lAdapter);
                                    // Genero un diálogo para preguntar al usuario si quiere reservar
                                    // el vuelo que ha seleccionado.
                                    lvVuelos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            MensajeDialogFragment mensaje = new MensajeDialogFragment();
                                            mensaje.registrarObserver(Vuelos.this, i);
                                            mensaje.show(getSupportFragmentManager(), "AlertDialog");
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No se ha podido establecer una conexión con la base de datos",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            db.collection("vuelos")
                    .whereEqualTo("Tipo", vuelo.getTipo())
                    .whereEqualTo("From", vuelo.getFrom())
                    .whereEqualTo("To", vuelo.getTo())
                    .whereEqualTo("Trasbordos", vuelo.getNumparadas())
                    .whereEqualTo("Depart", vuelo.getDepart())
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
                                    ListView lvVuelos = (ListView) findViewById(R.id.lvvuelos);
                                    ListAdapter lAdapter = new ListAdapter(getApplicationContext(), vuelos);
                                    lvVuelos.setAdapter(lAdapter);
                                    lvVuelos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            MensajeDialogFragment mensaje = new MensajeDialogFragment();
                                            mensaje.registrarObserver(Vuelos.this, i);
                                            mensaje.show(getSupportFragmentManager(), "AlertDialog");
                                        }
                                    });
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "No se ha podido establecer una conexión con la base de datos",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }


    }

    // En caso de que el usuario seleccione la opción "Sí" en el diálogo para realizar una reserva
    // este es el método que ejecuta el diálogo.
    public void update(int i) {
        // Primero recogo la información del item de la vista y genero un vuelo
        InformacionVuelo vuelo;
        vuelo = vuelos.get(i);
        Map<String, Object> vue = new HashMap<>();
        if (vuelos.get(i).getArrive() != null) {
            vue.put(Calendar.getInstance().getTime().toString(), Arrays.asList(vuelo.getTipo(), vuelo.getFrom(), vuelo.getTo(), vuelo.getNumparadas(), passengers, vuelo.getDepart(), vuelo.getArrive()));
        } else {
            vue.put(Calendar.getInstance().getTime().toString(), Arrays.asList(vuelo.getTipo(), vuelo.getFrom(), vuelo.getTo(), vuelo.getNumparadas(), passengers, vuelo.getDepart()));
        }
        // Una vez hecho esto, añado en la colección de reservas en el documento de mi usuario
        // un nuevo vuelo.
        db.collection("reservas").document(user.getEmail())
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
}
