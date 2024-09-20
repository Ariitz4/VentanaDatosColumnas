package es.aritzherrero.pantallarellenardatos;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase que representa a una persona, incluyendo su información personal y métodos de validación.
 */
public class Person {
    private static AtomicInteger personSequence = new AtomicInteger(0); // Contador único para el ID de la persona
    private int personId; // ID único de la persona
    private String firstName; // Nombre de la persona
    private String lastName; // Apellido de la persona
    private LocalDate birthDate; // Fecha de nacimiento de la persona

    /**
     * Enumeración que categoriza la edad de una persona.
     */
    public enum AgeCategory {
        BABY, CHILD, TEEN, ADULT, SENIOR, UNKNOWN
    };

    /**
     * Constructor por defecto que inicializa una persona vacía.
     */
    public Person() {
        this(null, null, null);
    }

    /**
     * Constructor que inicializa una nueva persona con los datos proporcionados.
     *
     * @param firstName Nombre de la persona
     * @param lastName Apellido de la persona
     * @param birthDate Fecha de nacimiento de la persona
     */
    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.personId = personSequence.incrementAndGet(); // Asigna un ID único a la persona
    }

    // Métodos de acceso (getters y setters)

    public int getPersonId() {
        return personId; // Retorna el ID de la persona
    }

    public void setPersonId(int personId) {
        this.personId = personId; // Establece el ID de la persona
    }

    public String getFirstName() {
        return firstName; // Retorna el nombre de la persona
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName; // Establece el nombre de la persona
    }

    public String getLastName() {
        return lastName; // Retorna el apellido de la persona
    }

    public void setLastName(String lastName) {
        this.lastName = lastName; // Establece el apellido de la persona
    }

    public LocalDate getBirthDate() {
        return birthDate; // Retorna la fecha de nacimiento de la persona
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate; // Establece la fecha de nacimiento de la persona
    }

    /**
     * Verifica si la fecha de nacimiento es válida.
     *
     * @param bdate La fecha de nacimiento a verificar
     * @return true si la fecha es válida, false de lo contrario
     */
    public boolean isValidBirthDate(LocalDate bdate) {
        return isValidBirthDate(bdate, new ArrayList<>()); // Llama al metodo con una lista de errores vacía
    }

    /**
     * Verifica si la fecha de nacimiento es válida y agrega errores a la lista si no lo es.
     *
     * @param bdate La fecha de nacimiento a verificar
     * @param errorList Lista para almacenar errores de validación
     * @return true si la fecha es válida, false de lo contrario
     */
    public boolean isValidBirthDate(LocalDate bdate, List<String> errorList) {
        if (bdate == null) {
            return true; // Si la fecha es nula, se considera válida
        }

        if (bdate.isAfter(LocalDate.now())) {
            errorList.add("Birth date must not be in future."); // Agrega un error si la fecha está en el futuro
            return false; // La fecha no es válida
        }
        return true; // La fecha es válida
    }

    /**
     * Verifica si la persona es válida y agrega errores a la lista si no lo es.
     *
     * @param errorList Lista para almacenar errores de validación
     * @return true si la persona es válida, false de lo contrario
     */
    public boolean isValidPerson(List<String> errorList) {
        return isValidPerson(this, errorList); // Llama al metodo de validación con la propia persona
    }

    /**
     * Verifica si la persona es válida y agrega errores a la lista si no lo es.
     *
     * @param p Persona a verificar
     * @param errorList Lista para almacenar errores de validación
     * @return true si la persona es válida, false de lo contrario
     */
    public boolean isValidPerson(Person p, List<String> errorList) {
        boolean isValid = true; // Inicializa la validez como verdadera
        String fn = p.getFirstName();
        if (fn == null || fn.trim().length() == 0) {
            errorList.add("First name must contain minimum one character."); // Agrega un error si el nombre es inválido
            isValid = false; // Cambia la validez a falsa
        }
        String ln = p.getLastName();
        if (ln == null || ln.trim().length() == 0) {
            errorList.add("Last name must contain minimum one character."); // Agrega un error si el apellido es inválido
            isValid = false; // Cambia la validez a falsa
        }
        if (!isValidBirthDate(this.getBirthDate(), errorList)) {
            isValid = false; // Si la fecha de nacimiento no es válida, cambia la validez a falsa
        }
        return isValid; // Retorna la validez de la persona
    }

    /**
     * Obtiene la categoría de edad de la persona.
     *
     * @return La categoría de edad de la persona
     */
    public AgeCategory getAgeCategory() {
        if (birthDate == null) {
            return AgeCategory.UNKNOWN; // Si la fecha de nacimiento es nula, retorna desconocido
        }
        long years = ChronoUnit.YEARS.between(birthDate, LocalDate.now()); // Calcula la diferencia en años
        if (years >= 0 && years < 2) {
            return AgeCategory.BABY; // Retorna bebé si la edad es menor a 2 años
        } else if (years >= 2 && years < 13) {
            return AgeCategory.CHILD; // Retorna niño si la edad está entre 2 y 12 años
        } else if (years >= 13 && years <= 19) {
            return AgeCategory.TEEN; // Retorna adolescente si la edad está entre 13 y 19 años
        } else if (years > 19 && years <= 50) {
            return AgeCategory.ADULT; // Retorna adulto si la edad está entre 20 y 50 años
        } else if (years > 50) {
            return AgeCategory.SENIOR; // Retorna senior si la edad es mayor a 50 años
        } else {
            return AgeCategory.UNKNOWN; // Retorna desconocido en caso de error
        }
    }

    /**
     * Guarda la persona si es válida, imprime un mensaje y retorna el estado de guardado.
     *
     * @param errorList Lista para almacenar errores de validación
     * @return true si se guardó la persona, false de lo contrario
     */
    public boolean save(List<String> errorList) {
        boolean isSaved = false; // Inicializa el estado de guardado como falso
        if (isValidPerson(errorList)) {
            System.out.println("Saved " + this.toString()); // Imprime un mensaje de éxito
            isSaved = true; // Cambia el estado de guardado a verdadero
        }
        return isSaved; // Retorna el estado de guardado
    }

    /**
     * Representa la información de la persona como una cadena.
     *
     * @return La representación en cadena de la persona
     */
    @Override
    public String toString() {
        return "[personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + "]"; // Retorna una cadena con los datos de la persona
    }
}
