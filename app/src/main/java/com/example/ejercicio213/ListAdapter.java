package com.example.ejercicio213;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    // Creo un adapatador para la actividad de Vuelos

    Context context;
    private ArrayList <InformacionVuelo> vuelos;

    public ListAdapter(Context context, ArrayList<InformacionVuelo> arrvuelos){
        this.context = context;
        this.vuelos = arrvuelos;
    }

    @Override
    public int getCount() {
        return vuelos.size();
    }
    @Override
    public Object getItem(int i) {
        return vuelos.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final View result;
        // Instancio todos los campos
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row, parent, false);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.appIconIV);
            viewHolder.txtTipo = (TextView) convertView.findViewById(R.id.tvtipo);
            viewHolder.txtFrom = (TextView) convertView.findViewById(R.id.tvfrom);
            viewHolder.txtTo = (TextView) convertView.findViewById(R.id.tvto);
            viewHolder.txtFecha = (TextView) convertView.findViewById(R.id.tvfecha);
            viewHolder.txtParadas = (TextView) convertView.findViewById(R.id.tvparadas);
            viewHolder.txtPrecio = (TextView) convertView.findViewById(R.id.tvprecio);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // En caso de que el vuelo sea de Ida y vuelta, añado la vuelta a la derecha de la ida
        // en la misma fila

        if (vuelos.get(position).tipo.equalsIgnoreCase("Ida y Vuelta")) {
            viewHolder.txtFecha.setText(vuelos.get(position).getDepart().concat(" / ").concat(vuelos.get(position).getArrive()));
        } else {
            viewHolder.txtFecha.setText(vuelos.get(position).getDepart());
        }

        viewHolder.txtTipo.setText(vuelos.get(position).getTipo());
        viewHolder.txtFrom.setText(vuelos.get(position).getFrom());
        viewHolder.txtTo.setText(vuelos.get(position).getTo());
        viewHolder.txtParadas.setText(vuelos.get(position).getNumparadas());
        viewHolder.txtPrecio.setText(vuelos.get(position).getPrecio());


        return convertView;
    }
    private static class ViewHolder {
        ImageView icon;
        TextView txtTipo;
        TextView txtFrom;
        TextView txtTo;
        TextView txtFecha;
        TextView txtParadas;
        TextView txtPrecio;
    }
}
