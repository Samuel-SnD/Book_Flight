package com.example.ejercicio213;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Reservas extends AppCompatActivity {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    ArrayList<InformacionVuelo> vuelos = new ArrayList <> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        db.collection("reservas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equalsIgnoreCase(user.getEmail())) {
                                    Map<String, Object> m = document.getData();
                                    for (String key: m.keySet()) {
                                        List<Object> lista = (List <Object>) m.get(key);
                                        InformacionVuelo vuelo = new InformacionVuelo();
                                        vuelo.setTipo((String) lista.get(0));
                                        vuelo.setFrom((String) lista.get(1));
                                        vuelo.setTo((String) lista.get(2));
                                        vuelo.setNumparadas((String) lista.get(3));
                                        vuelo.setPassengers((Long) lista.get(4));
                                        vuelo.setDepart((String) lista.get(5));
                                        if (lista.size() == 7)
                                            vuelo.setArrive((String) lista.get(6));
                                        vuelos.add(vuelo);
                                    }
                                    ListView lvreservas = (ListView) findViewById(R.id.lvreservas);
                                    ListAdapter2 lAdapter = new ListAdapter2(getApplicationContext(), vuelos);
                                    lvreservas.setAdapter(lAdapter);
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No se ha podido establecer una conexión con la base de datos",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}