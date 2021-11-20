package com.example.ejercicio213;

import java.io.Serializable;

public class InformacionVuelo implements Serializable {
    String tipo, numparadas, from, to, depart, arrive;
    int passengers;
    float precio;

    public InformacionVuelo (String ti, String f, String t, String d, String a, String num, int p) {
        this.tipo = ti;
        this.from = f;
        this.to = t;
        this.depart = d;
        this.arrive = a;
        this.numparadas = num;
        this.passengers = p;
    }
    public InformacionVuelo (String ti, String f, String t, String d, String num, int p) {
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

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public float getPrecio () {return precio;}

    public void setPrecio (float precio) {this.precio = precio;}


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
