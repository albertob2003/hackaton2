package com.hackathon.agenda.model;

import java.util.Locale;
import java.util.Objects;

public class Contacto {
    private String nombre;
    private String apellido;
    private String telefono;

    public Contacto(String nombre, String apellido, String telefono) {
        this.nombre = nombre == null ? null : nombre.trim();
        this.apellido = apellido == null ? null : apellido.trim();
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre == null ? null : nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido == null ? null : apellido.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Validar nombre y apellido

    public boolean esValido() {
        if (nombre == null || apellido == null) {
            return false;
        }
        if (nombre.trim().isEmpty() || apellido.trim().isEmpty()) {
            return false;
        }

        return true;
    }// boolean esValido

    // Comparar contactos
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Contacto)) {
            return false;
        }

        Contacto otroContacto = (Contacto) obj;

        // Evitar comparar nombres o apellidos invalidos
        if (!esValido() || !otroContacto.esValido()) {
            return false;
        }

        return nombre.equalsIgnoreCase(otroContacto.nombre)
                && apellido.equalsIgnoreCase(otroContacto.apellido);
    } // @Override boolean equals


   @Override
    public int hashCode() {
        // manejo de contactos invalidos
       if (!esValido()){
           return System.identityHashCode(this);
       }

       //Comparar sin distinguir mayusculas
       return Objects.hash(nombre.toLowerCase(Locale.ROOT),
               apellido.toLowerCase(Locale.ROOT));
   }// @Override int hashCode

    // Mostrar contacto
    @Override
    public String toString(){
        return nombre + " " + apellido + " - " + telefono;
    }

}