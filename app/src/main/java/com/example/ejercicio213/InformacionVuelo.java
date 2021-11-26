package com.example.ejercicio213;

import java.io.Serializable;

public class InformacionVuelo implements Serializable {
    // Este objeto se utilizará a lo largo del programa para generar vuelos.
    String tipo, numparadas, from, to, depart, arrive;
    long passengers;
    String precio, idreserva;

    // Este constructor se usa cuando el vuelo es de ida y vuelta.
    public InformacionVuelo (String ti, String f, String t, String d, String a, String num, long p) {
        this.tipo = ti;
        this.from = f;
        this.to = t;
        this.depart = d;
        this.arrive = a;
        this.numparadas = num;
        this.passengers = p;
    }

    // Este constructor se usa cuando el vuelo es únicamente de ida.
    public InformacionVuelo (String ti, String f, String t, String d, String num, long p) {
        this.tipo = ti;
        this.from = f;
        this.to = t;
        this.depart = d;
        this.numparadas = num;
        this.passengers = p;
    }

    public InformacionVuelo(){}

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumparadas() {
        return numparadas;
    }

    public void setNumparadas(String numparadas) {
        this.numparadas = numparadas;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public long getPassengers() {
        return passengers;
    }

    public void setPassengers(long passengers) {
        this.passengers = passengers;
    }

    public String getPrecio () {return precio;}

    public void setPrecio (String precio) {this.precio = precio;}

    public String getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(String idreserva) {
        this.idreserva = idreserva;
    }

    // Genero un toString para poder enseñar los vuelos en caso de ser necesario mostrarlos en un Log
    @Override
    public String toString() {
        return  "Tipo = " + tipo + '\n' +
                "   Paradas = " + numparadas + '\n' +
                "   From = " + from + '\n' +
                "   To = " + to + '\n' +
                "   Depart = " + depart + '\n' +
                "   Arrive = " + arrive + '\n' +
                "   Passengers = " + passengers;
    }
}
