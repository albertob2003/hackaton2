#  Sistema de Gestión de Agenda Telefónica - Hackathon

Sistema de gestión de contactos desarrollado en **Java**, diseñado originalmente como aplicación de consola y estructurado bajo una sólida arquitectura orientada a objetos (POO).

---

##  Integrantes del Equipo y Roles

El trabajo y las responsabilidades del proyecto se distribuyeron de forma equitativa entre los 5 integrantes:

*   **Manuel Rodríguez**
    *   **Rol:** Interfaz de Consola y Menú Principal (`MainApp.java`).
    *   **Responsabilidades:** Creación de la clase principal, diseño del menú interactivo mediante bucles y gestión limpia de la entrada de datos por consola con `Scanner`.
*   **Luis Fernando Sánchez**
    *   **Rol:** Modelo de Datos y Validaciones Base (`Contacto.java`).
    *   **Responsabilidades:** Estructuración de la entidad `Contacto`, encapsulamiento, sobreescritura de `equals()` y `hashCode()` para la validación estricta de duplicados sin distinguir entre mayúsculas y minúsculas.
*   **Alexandra Pichardo**
    *   **Rol:** Estructura de Colección y Control de Capacidad (`Agenda.java`).
    *   **Responsabilidades:** Gestión del almacenamiento interno, implementación de constructores para capacidad personalizada o por defecto (10 contactos), y control de límites con `agendaLlena()` y `espacioLibres()`.
*   **Alberto Benítez**
    *   **Rol:** Operaciones CRUD (`Agenda.java`).
    *   **Responsabilidades:** Lógica de negocio para altas (`anadirContacto`), validación de existencia (`existeContacto`), búsquedas avanzadas (`buscaContacto`), bajas (`eliminarContacto`) y actualizaciones (`modificarTelefono`).
*   **Ángel Ochoa**
    *   **Rol:** Visualización y Ordenamiento Alfabético (`Agenda.java`).
    *   **Responsabilidades:** Implementación del ordenamiento eficiente mediante `Collections.sort` y `Comparator` para ordenar los registros alfabéticamente por nombre y apellido antes de presentarlos formateados en pantalla.

---

##  Estructura de Carpetas

```text
Hackaton2/
├── .idea/
├── out/
├── src/
│   └── com/
│       ├── hackathon.agenda/
│           ├── app/
│           │   └── MainApp.java
│           ├── model/
│           │   └── Contacto.java
│           └── service/
│               └── Agenda.java
│       
│           
├── .gitignore
└── README.md
```

---

## Código Fuente del Proyecto

### 1. `src/com/hackathon/agenda/model/Contacto.java`
*(Desarrollado por: Luis Fernando Sánchez)*

```java
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
```

### 2. `src/com/hackathon/agenda/service/Agenda.java`
*(Desarrollado por: Alexandra Pichardo, Ángel Ochoa y Alberto Benítez)*

```java
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
    }

    public Agenda(int tamanioMaximo) {
        this.tamanioMaximo = tamanioMaximo;
        this.contactos = new ArrayList<>();
    }

    public boolean agendaLlena() {
        return contactos.size() >= tamanioMaximo;
    }

    public int espacioLibres() {
        int libres = tamanioMaximo - contactos.size();
        System.out.println("Espacios libres disponibles: " + libres + " de " + tamanioMaximo);
        return libres;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public boolean existeContacto(Contacto c) {
        if (c == null) return false;
        return contactos.contains(c);
    }

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

    public void añadirContacto(Contacto c) {
        anadirContacto(c);
    }

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

    public void eliminarContacto(Contacto c) {
        if (contactos.remove(c)) {
            System.out.println("¡Contacto eliminado con éxito!");
        } else {
            System.out.println("No se pudo eliminar: El contacto no existe en la agenda.");
        }
    }

    // 5. modificarTelefono(...)
    public void modificarTelefono(String nombre, String apellido, String nuevoTelefono) {

        boolean encontrado = false;

        for (Contacto contacto : contactos) {

            if (contacto.getNombre().trim().equalsIgnoreCase(nombre.trim())
                    && contacto.getApellido().trim().equalsIgnoreCase(apellido.trim())) {

                contacto.setTelefono(nuevoTelefono);

                System.out.println("\n¡Número actualizado correctamente!");

                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("\nEl contacto no existe en la agenda.");
        }
    }

    public void listarContactos() {
        if (contactos.isEmpty()) {
            System.out.println("La agenda no contiene contactos.");
            return;
        }

        Collections.sort(contactos, new Comparator<Contacto>() {
            @Override
            public int compare(Contacto c1, Contacto c2) {
                int resultadoNombre = c1.getNombre().compareToIgnoreCase(c2.getNombre());
                if (resultadoNombre != 0) {
                    return resultadoNombre;
                }
                return c1.getApellido().compareToIgnoreCase(c2.getApellido());
            }
        });

        System.out.println("\n========== AGENDA DE CONTACTOS ==========");
        System.out.println("-----------------------------------------");
        for (Contacto contacto : contactos) {
            System.out.println(contacto);
        }
        System.out.println("-----------------------------------------");
        System.out.println("Total de contactos: " + contactos.size());
    }
}
```

### 3. `src/com/hackathon/agenda/MainApp.java`
*(Desarrollado por: Manuel Rodríguez)*

```java
package com.hackathon.agenda;

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
    }

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
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no numérica. Se asigna tamaño por defecto (10).");
                agenda = new Agenda();
            }
        } else {
            agenda = new Agenda();
            System.out.println("Agenda creada con capacidad por defecto de 10 contactos.");
        }
    }

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
                        System.out.println("> [ALERTA]: La agenda está completamente LLENA.");
                    } else {
                        System.out.println("> [INFO]: La agenda tiene espacios disponibles.");
                    }
                }
                case "8" -> agenda.espacioLibres();
                case "0" -> salir = true;
                default -> System.out.println("> [ERROR]: Opción no válida. Intenta nuevamente.");
            }
        } while (!salir);
    }

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
    }

    private static void opcionAnadirContacto() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        Contacto c = new Contacto(nombre, apellido, telefono);
        agenda.anadirContacto(c);
    }

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
        }
    }

    private static void opcionBuscarContacto() {
        System.out.print("Nombre del contacto a buscar: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido del contacto a buscar: ");
        String apellido = scanner.nextLine();

        agenda.buscaContacto(nombre, apellido);
    }

    private static void opcionEliminarContacto() {
        System.out.print("Nombre del contacto a eliminar: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido del contacto a eliminar: ");
        String apellido = scanner.nextLine();

        Contacto c = new Contacto(nombre, apellido, "");
        agenda.eliminarContacto(c);
    }

    private static void opcionModificarTelefono() {
        System.out.print("Nombre del contacto: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido del contacto: ");
        String apellido = scanner.nextLine();
        System.out.print("Nuevo teléfono: ");
        String nuevoTelefono = scanner.nextLine();

        agenda.modificarTelefono(nombre, apellido, nuevoTelefono);
    }
}
```

---

## Instrucciones de Ejecución

1. **Compilar el proyecto:**
   ```bash
   javac -d bin -sourcepath src src/com/hackathon/agenda/MainApp.java
   ```

2. **Ejecutar la aplicación:**
   ```bash
   java -cp bin com.hackathon.agenda.MainApp