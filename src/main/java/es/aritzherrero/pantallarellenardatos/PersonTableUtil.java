package es.aritzherrero.pantallarellenardatos;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Clase utilitaria que proporciona métodos para crear y manejar una lista de personas
 * y sus columnas en una tabla.
 */
public class PersonTableUtil {

    /**
     * Crea y devuelve una lista observable de personas con datos de ejemplo.
     *
     * @return Una lista observable de objetos Person
     */
    public static ObservableList<Person> getPersonList() {
        // Crea instancias de Person con datos de ejemplo
        Person p1 = new Person("Ashwin", "Sharan", LocalDate.of(2012, 10, 11));
        Person p2 = new Person("Advik", "Sharan", LocalDate.of(2012, 10, 11));
        Person p3 = new Person("Layne", "Estes", LocalDate.of(2011, 12, 16));
        Person p4 = new Person("Mason", "Boyd", LocalDate.of(2003, 4, 20));
        Person p5 = new Person("Babalu", "Sharan", LocalDate.of(1980, 1, 10));
        // Devuelve una lista observable que contiene las personas creadas
        return FXCollections.observableArrayList(p1, p2, p3, p4, p5);
    }

    /**
     * Crea y devuelve una columna para el ID de la persona.
     *
     * @return Una TableColumn que muestra el ID de la persona
     */
    public static TableColumn<Person, Integer> getIdColumn() {
        TableColumn<Person, Integer> personIdCol = new TableColumn<>("Id"); // Crea la columna con el título "Id"
        personIdCol.setCellValueFactory(new PropertyValueFactory<>("personId")); // Asocia la columna al campo personId
        return personIdCol; // Retorna la columna
    }

    /**
     * Crea y devuelve una columna para el nombre de la persona.
     *
     * @return Una TableColumn que muestra el nombre de la persona
     */
    public static TableColumn<Person, String> getFirstNameColumn() {
        TableColumn<Person, String> fNameCol = new TableColumn<>("First Name"); // Crea la columna con el título "First Name"
        fNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName")); // Asocia la columna al campo firstName
        return fNameCol; // Retorna la columna
    }

    /**
     * Crea y devuelve una columna para el apellido de la persona.
     *
     * @return Una TableColumn que muestra el apellido de la persona
     */
    public static TableColumn<Person, String> getLastNameColumn() {
        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name"); // Crea la columna con el título "Last Name"
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName")); // Asocia la columna al campo lastName
        return lastNameCol; // Retorna la columna
    }

    /**
     * Crea y devuelve una columna para la fecha de nacimiento de la persona.
     *
     * @return Una TableColumn que muestra la fecha de nacimiento de la persona
     */
    public static TableColumn<Person, LocalDate> getBirthDateColumn() {
        TableColumn<Person, LocalDate> bDateCol = new TableColumn<>("Birth Date"); // Crea la columna con el título "Birth Date"
        bDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate")); // Asocia la columna al campo birthDate
        return bDateCol; // Retorna la columna
    }
}


