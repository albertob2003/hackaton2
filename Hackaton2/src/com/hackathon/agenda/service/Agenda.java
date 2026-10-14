package com.hackathon.agenda.service;

import com.hackathon.agenda.model.Contacto;
import java.util.Collections;
import java.util.Comparator;
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
    } // Verificacion del limite del almacenamiento, true = llena, false = aun hay espacio

    public int espacioLibres() {
        int libres = tamanioMaximo - contactos.size();
        System.out.println("Espacios libres disponibles: " + libres + " de " + tamanioMaximo);
        return libres;
    } // calcula la cantidad de espacios libres que tiene la agenda

    public List<Contacto> getContactos() {
        return contactos;
    } // muestra la lista completa de contactos registrados


    // --- MÉTODOS SOLICITADOS PARA EL CRUD Y VALIDACIONES ---

    // 1. existeContacto(Contacto c)
    public boolean existeContacto(Contacto c) {
        if (c == null) return false;
        return contactos.contains(c);
    }

    // 2. añadirContacto(Contacto c)
    public void anadirContacto(Contacto c) {
        if (agendaLlena()) {
            System.out.println("No se puede añadir: La agenda está llena.");
            return;
        }
        if (c.getNombre() == null || c.getNombre().trim().isEmpty() ||
                c.getApellido() == null || c.getApellido().trim().isEmpty()) {
            System.out.println("Error: El nombre y el apellido no pueden estar vacíos.");
            return;
        }
        if (existeContacto(c)) {
            System.out.println("Error: El contacto ya existe en la agenda.");
            return;
        }

        contactos.add(c);
        System.out.println("¡Contacto añadido con éxito!");
    }

    // Alias para garantizar compatibilidad si se invoca con 'ñ' o sin 'ñ'
    public void añadirContacto(Contacto c) {
        anadirContacto(c);
    }

    // 3. buscaContacto(String nombre, String apellido)
    public void buscaContacto(String nombre, String apellido) {
        boolean encontrado = false;
        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(nombre) && c.getApellido().equalsIgnoreCase(apellido)) {
                System.out.println("Teléfono de " + c.getNombre() + " " + c.getApellido() + ": " + c.getTelefono());
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("No encontrado.");
        }
    }

    // 4. eliminarContacto(Contacto c)
    public void eliminarContacto(Contacto c) {
        if (contactos.remove(c)) {
            System.out.println("¡Contacto eliminado con éxito!");
        } else {
            System.out.println("No se pudo eliminar: El contacto no existe en la agenda.");
        }
    }

    // 5. modificarTelefono(...)
    public void modificarTelefono(String nombre, String apellido, String nuevoTelefono) {

    }

    public void listarContactos() {
    }


    // 6. listarContactos()
    public void listarContactos() {

        // Verificar si la agenda está vacía
        if (contactos.isEmpty()) {
            System.out.println("La agenda no contiene contactos.");
            return;
        }

        // Ordenar por nombre y después por apellido
        Collections.sort(contactos, new Comparator<Contacto>() {
            @Override
            public int compare(Contacto c1, Contacto c2) {

                int resultadoNombre = c1.getNombre()
                        .compareToIgnoreCase(c2.getNombre());

                if (resultadoNombre != 0) {
                    return resultadoNombre;
                }

                return c1.getApellido()
                        .compareToIgnoreCase(c2.getApellido());
            }
        });

        // Mostrar los contactos ordenados
        System.out.println("\n========== AGENDA DE CONTACTOS ==========");
        System.out.println("-----------------------------------------");

        for (Contacto contacto : contactos) {
            System.out.println(contacto);
        }

        System.out.println("-----------------------------------------");
        System.out.println("Total de contactos: " + contactos.size());
    }

} // <--- Esta única llave cierra correctamente la clase Agenda