package com.agenda;

import com.hackathon.agenda.model.Contacto;
import com.hackathon.agenda.service.Agenda;

import java.util.Scanner;

public class MainApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static Agenda agenda;

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   SISTEMA DE GESTIÓN DE AGENDA TELEFÓNICA");
        System.out.println("=================================================");

        inicializarAgenda();
        ejecutarMenu();

        scanner.close();
        System.out.println("\n¡Gracias por utilizar nuestra agenda!");

    }// main

    private static void inicializarAgenda() {
        System.out.println("\n¿Cómo deseas crear la agenda?");
        System.out.println("1. Indicar tamaño máximo personalizado");
        System.out.println("2. Usar tamaño por defecto (10 contactos)");
        System.out.print("Selecciona una opción (1-2): ");

        String opcion = scanner.nextLine().trim();
        if ("1".equals(opcion)) {
            System.out.print("Ingresa el tamaño máximo: ");
            try {
                int tamano = Integer.parseInt(scanner.nextLine().trim());
                if (tamano <= 0) {
                    System.out.println("Tamaño inválido. Se asigna tamaño por defecto (10).");
                    agenda = new Agenda();
                } else {
                    agenda = new Agenda(tamano);
                    System.out.println("Agenda creada con capacidad para " + tamano + " contactos.");

                }//else

            } catch (NumberFormatException e) {
                System.out.println("Entrada no numérica. Se asigna tamaño por defecto (10).");
                agenda = new Agenda();

            }//catch

        } else {

            agenda = new Agenda();
            System.out.println("Agenda creada con capacidad por defecto de 10 contactos.");
        }// else

    }// inicializarAgenda

    private static void ejecutarMenu() {
        boolean salir = false;

        do {
            mostrarMenu();
            System.out.print("Elige una opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> opcionAnadirContacto();
                case "2" -> opcionExisteContacto();
                case "3" -> agenda.listarContactos();
                case "4" -> opcionBuscarContacto();
                case "5" -> opcionEliminarContacto();
                case "6" -> opcionModificarTelefono();
                case "7" -> {
                    if (agenda.agendaLlena()) {
                        System.out.println("[ALERTA]: La agenda está completamente LLENA.");
                    } else {
                        System.out.println("[INFO]: La agenda tiene espacio disponible.");
                    }//else
                }// case 7
                case "8" -> agenda.espacioLibres();
                case "0" -> salir = true;
                default -> System.out.println("[ERROR]: Opción no válida. Intenta nuevamente.");

            }// switch
        } while (!salir);
    }// ejecutarMenu

    private static void mostrarMenu() {
        System.out.println("\n=============== MENÚ AGENDA ====================");
        System.out.println("> 1. Añadir contacto");
        System.out.println("> 2. Verificar si existe contacto");
        System.out.println("> 3. Listar contactos (ordenados)");
        System.out.println("> 4. Buscar contacto por nombre y apellido");
        System.out.println("> 5. Eliminar contacto");
        System.out.println("> 6. Modificar teléfono");
        System.out.println("> 7. Comprobar si la agenda está llena");
        System.out.println("> 8. Ver espacios libres");
        System.out.println("> 0. Salir");
        System.out.println("=====================================================");

    }// private mostrarMenu

    private static void opcionAnadirContacto() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        Contacto c = new Contacto(nombre, apellido, telefono);
        agenda.anadirContacto(c);

    }// opcionAnadirContacto

    private static void opcionExisteContacto() {
        System.out.print("Nombre a verificar: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido a verificar: ");
        String apellido = scanner.nextLine();

        Contacto c = new Contacto(nombre, apellido, "");
        if (agenda.existeContacto(c)) {
            System.out.println("> [RESULTADO]: El contacto " + nombre + " " + apellido + " SÍ existe.");
        } else {
            System.out.println("> [RESULTADO]: El contacto " + nombre + " " + apellido + " NO existe.");
        }//else

    }// opcionExisteContacto

    private static void opcionBuscarContacto() {
        System.out.print("Nombre del contacto a buscar: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido del contacto a buscar: ");
        String apellido = scanner.nextLine();

        agenda.buscaContacto(nombre, apellido);
    }//opcionBuscarContacto

    private static void opcionEliminarContacto() {
        System.out.print("Nombre del contacto a eliminar: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido del contacto a eliminar: ");
        String apellido = scanner.nextLine();

        Contacto c = new Contacto(nombre, apellido, "");
        agenda.eliminarContacto(c);

    }//opcionEliminarContacto

    private static void opcionModificarTelefono() {
        System.out.print("Nombre del contacto: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido del contacto: ");
        String apellido = scanner.nextLine();
        System.out.print("Nuevo teléfono: ");
        String nuevoTelefono = scanner.nextLine();

        agenda.modificarTelefono(nombre, apellido, nuevoTelefono);

    }//opcionModificarTelefono

}// class MainApp