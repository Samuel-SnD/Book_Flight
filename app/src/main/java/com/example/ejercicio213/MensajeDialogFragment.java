package com.example.ejercicio213;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Observable;

public class MensajeDialogFragment extends DialogFragment {

    private Context context;
    private int posicion;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Genero un diálogo que pregunta al usuario si quiere reservar el vuelo sobre el que
        // ha hecho click.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title);
        builder.setIcon(R.drawable.plane);
        builder.setMessage(R.string.dialog1);
        // En caso de que el usuario quiera hacer la reserva se llama al método update()
        // de la clase Vuelos pasándole la posición del item.
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ((Vuelos) context).update(posicion);
            }
        });
        // En caso de que el usuario no quiera hacer la reserva se cierra el diálogo.
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        return builder.create();
    }

    // El método registrarObserver() se utiliza para que al hacer click en "Sí" en el diálogo
    // se pueda utilizar el método update() con la posición del item.
    public void registrarObserver (Vuelos activity, int i) {
        context = activity;
        posicion = i;
    }

}
