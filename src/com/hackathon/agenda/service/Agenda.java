package com.hackathon.agenda.service;

import com.hackathon.agenda.model.Contacto;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<Contacto> contactos;
    private int tamanioMaximo;

    public Agenda() {
        this.tamanioMaximo = 10;
        this.contactos = new ArrayList<>();
    } // Constructor

    public Agenda(int tamanioMaximo) {
        this.tamanioMaximo = tamanioMaximo;
        this.contactos = new ArrayList<>();
    }// Define el tamaño de la agenda

    public boolean agendaLlena() {
        return contactos.size() >= tamanioMaximo;
    } //Verificacion del limite del almacenamiento, true = llena, false = aun hay espacio

    public int espacioLibres() {
        return tamanioMaximo - contactos.size();
    } // calcula la cantidad de espacios libre tiene la agenda

    public List<Contacto> getContactos() {
        return contactos;
    } // muestra la lista completa de contactos registrados
}
