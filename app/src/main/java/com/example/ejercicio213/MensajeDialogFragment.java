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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title);
        builder.setIcon(R.drawable.plane);
        builder.setMessage(R.string.dialog1);
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ((Vuelos) context).update(posicion);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        return builder.create();
    }

    public void registrarObserver (Vuelos activity, int i) {
        context = activity;
        posicion = i;
    }

}
