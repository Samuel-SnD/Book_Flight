package com.example.ejercicio213;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

public class Historial extends AppCompatActivity {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    ArrayList<InformacionVuelo> vuelos = new ArrayList <> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        // Realizo una consulta a la base de datos para que me devuelva el historial del usuario
        // que está conectado en este momento.
        db.collection("historial")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equalsIgnoreCase(user.getEmail())) {
                                    Map <String, Object> m = document.getData();
                                    for (String key: m.keySet()) {
                                        // Genero un objeto por cada resultado que añado a un
                                        // ArrayList de vuelos que se mostrarán en la vista
                                        List <Object> lista = (List <Object>) m.get(key);
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
                                    // Creo la ListView y un adaptador para la vista con el
                                    // ArrayList que he creado anteriormente.
                                    ListView lvhistorial = (ListView) findViewById(R.id.lvhistorial);
                                    ListAdapter2 lAdapter = new ListAdapter2(getApplicationContext(), vuelos);
                                    lvhistorial.setAdapter(lAdapter);
                                }
                            }
                        } else {
                            // En caso de que no se pueda establecer una conexión con la base de datos
                            // se lo comunico al usuario.
                            Toast.makeText(getApplicationContext(), "No se ha podido establecer una conexión con la base de datos",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}