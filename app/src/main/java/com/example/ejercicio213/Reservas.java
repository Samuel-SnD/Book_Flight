package com.example.ejercicio213;

import android.content.Intent;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reservas extends AppCompatActivity {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DocumentReference docReservas = db.collection("reservas").document(user.getEmail());
    ArrayList<InformacionVuelo> vuelos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        // Realizo una consulta a la base de datos para que me devuelva
        // todos los datos de la colección reservas
        db.collection("reservas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Ahora compruebo si la colleción es la de mi usuario
                                if (document.getId().equalsIgnoreCase(user.getEmail())) {
                                    // Genero los vuelos con lo que obtengo
                                    Map<String, Object> m = document.getData();
                                    for (String key : m.keySet()) {
                                        List<Object> lista = (List<Object>) m.get(key);
                                        InformacionVuelo vuelo = new InformacionVuelo();
                                        vuelo.setTipo((String) lista.get(0));
                                        vuelo.setFrom((String) lista.get(1));
                                        vuelo.setTo((String) lista.get(2));
                                        vuelo.setNumparadas((String) lista.get(3));
                                        vuelo.setPassengers((Long) lista.get(4));
                                        vuelo.setDepart((String) lista.get(5));
                                        vuelo.setIdreserva(key);
                                        if (lista.size() == 7)
                                            vuelo.setArrive((String) lista.get(6));
                                        vuelos.add(vuelo);
                                    }
                                    // Genero la vista con el adaptador correspondiente
                                    ListView lvreservas = (ListView) findViewById(R.id.lvreservas);
                                    ListAdapter2 lAdapter = new ListAdapter2(getApplicationContext(), vuelos);
                                    lvreservas.setAdapter(lAdapter);
                                    registerForContextMenu(lvreservas);
                                }
                            }
                        } else {
                            // En caso de que no se pueda conectar con la base de datos muestro
                            // un error al usuario.
                            Toast.makeText(getApplicationContext(), "No se ha podido establecer una conexión con la base de datos",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    // Con los siguientes dos métodos genero el menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menureservas, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        // Hago un switch para iterar sobre el objeto dependiendo de la opción que escoja el usuario
        switch (item.getItemId()) {
            // La primera opción genera un intent a google maps para llevar al usuario al aeropuerto
            // de la ciudad de donde sale el vuelo en cuestión.
            case R.id.Cm1:
                if (vuelos.get(info.position).getFrom().equalsIgnoreCase("A Coruña")) {
                    Uri gmmIntentUri = Uri.parse("geo:43.3022271,-8.3835869");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                } else if (vuelos.get(info.position).getFrom().equalsIgnoreCase("Murcia")) {
                    Uri gmmIntentUri = Uri.parse("geo:37.8044768,-1.1335198");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                } else if (vuelos.get(info.position).getFrom().equalsIgnoreCase("New York")) {
                    Uri gmmIntentUri = Uri.parse("geo:40.6413111,-73.7803278");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                } else if (vuelos.get(info.position).getFrom().equalsIgnoreCase("Santiago de Compostela")) {
                    Uri gmmIntentUri = Uri.parse("geo:42.8967147,-8.4183738");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                return true;
            // La segunda opción genera un intent a google maps para llevar al usuario al aeropuerto
            // de la ciudad destino del vuelo en cuestión.
            case R.id.Cmenu2:
                if (vuelos.get(info.position).getTo().equalsIgnoreCase("A Coruña")) {
                    Uri gmmIntentUri = Uri.parse("geo:43.3022271,-8.3835869");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                } else if (vuelos.get(info.position).getTo().equalsIgnoreCase("Murcia")) {
                    Uri gmmIntentUri = Uri.parse("geo:37.8044768,-1.1335198");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                } else if (vuelos.get(info.position).getTo().equalsIgnoreCase("New York")) {
                    Uri gmmIntentUri = Uri.parse("geo:40.6413111,-73.7803278");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                } else if (vuelos.get(info.position).getTo().equalsIgnoreCase("Santiago de Compostela")) {
                    Uri gmmIntentUri = Uri.parse("geo:42.8967147,-8.4183738");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                return true;
            // La tercera opción genera un evento en Google Calendar recogiendo los datos del vuelo
            // y poniendo como fecha el día anterior a la salida.
            case R.id.Cmenu3:
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setData(CalendarContract.Events.CONTENT_URI);
                calIntent.putExtra(CalendarContract.Events.TITLE, "Aviso de vuelo");
                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, vuelos.get(info.position).getFrom());
                calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Vuelo a: " + vuelos.get(info.position).getTo());
                Calendar startTime = Calendar.getInstance();
                String[] fecha = vuelos.get(info.position).getDepart().split("/");
                startTime.set(2021, Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]), 18, 0);
                if (vuelos.get(info.position).getArrive() != null) {
                    Calendar endTime = Calendar.getInstance();
                    String[] fecha2 = vuelos.get(info.position).getArrive().split("/");
                    endTime.set(2021, Integer.parseInt(fecha2[1]), Integer.parseInt(fecha2[0]), 22, 30);
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
                }
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime.getTimeInMillis());
                startActivity(calIntent);
                return true;
            // La cuarta opción permite modificar la reserva añadiendo un pasajero más a la misma.
            case R.id.Cmenu4:
                InformacionVuelo vuelo = vuelos.get(info.position);
                vuelo.setPassengers(vuelo.getPassengers() + 1);
                Map <String, Object> mapa = new HashMap <> ();
                if (vuelos.get(info.position).getArrive() != null) {
                    mapa.put(vuelos.get(info.position).getIdreserva(), Arrays.asList(vuelo.getTipo(), vuelo.getFrom(), vuelo.getTo(), vuelo.getNumparadas(), vuelo.getPassengers(), vuelo.getDepart(), vuelo.getArrive()));
                } else {
                    mapa.put(vuelos.get(info.position).getIdreserva(), Arrays.asList(vuelo.getTipo(), vuelo.getFrom(), vuelo.getTo(), vuelo.getNumparadas(), vuelo.getPassengers(), vuelo.getDepart()));
                }
                docReservas.update(mapa).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Si se realiza correctamente se recarga la vista
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Se ha añadido un pasajero más al vuelo", Toast.LENGTH_SHORT).show();
                            ListView lvreservas = (ListView) findViewById(R.id.lvreservas);
                            ListAdapter2 lAdapter = new ListAdapter2(getApplicationContext(), vuelos);
                            lvreservas.setAdapter(lAdapter);
                            registerForContextMenu(lvreservas);
                        } else {
                            Toast.makeText(getApplicationContext(), "No se ha podido añadir un vuelo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return true;
            // La quinta opción permite elminar una reserva de la lista.
            case R.id.Cmenu5:
                Map <String, Object> map = new HashMap <> ();
                map.put(vuelos.get(info.position).getIdreserva(), FieldValue.delete());
                docReservas.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Si se realiza correctamente se recarga la vista.
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Se ha eliminado la reserva correctamente", Toast.LENGTH_LONG).show();
                            vuelos.remove(vuelos.get(info.position));
                            ListView lvreservas = (ListView) findViewById(R.id.lvreservas);
                            ListAdapter2 lAdapter = new ListAdapter2(getApplicationContext(), vuelos);
                            lvreservas.setAdapter(lAdapter);
                            registerForContextMenu(lvreservas);
                        } else {
                            Toast.makeText(getApplicationContext(), "No se ha podido eliminar la reserva", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}