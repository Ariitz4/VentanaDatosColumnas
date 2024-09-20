package es.aritzherrero.pantallarellenardatos;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Arrays;

import javafx.scene.layout.HBox;
import static javafx.scene.control.TableView.TableViewSelectionModel;

/**
 * Clase principal que representa la ventana de la aplicación para
 * agregar y eliminar filas en una tabla de personas.
 */
public class VentanaPrincipal extends Application {
    private TextField fNameField; // Campo para el nombre
    private TextField lNameField; // Campo para el apellido
    private DatePicker dobField; // Campo para la fecha de nacimiento
    private TableView<Person> table; // Tabla para mostrar los datos de las personas

    /**
     * Metodo principal que inicia la aplicación.
     *
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        Application.launch(args); // Lanza la aplicación
    }

    /**
     * Metodo que se ejecuta al iniciar la aplicación.
     *
     * @param stage La ventana principal de la aplicación
     */
    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) {
        // Inicialización de los campos de entrada
        fNameField = new TextField();
        lNameField = new TextField();
        dobField = new DatePicker();
        // Inicializa la tabla con la lista de personas
        table = new TableView<>(PersonTableUtil.getPersonList());

        // Configura la selección múltiple en la tabla
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);

        // Agrega columnas a la tabla
        table.getColumns().addAll(
                PersonTableUtil.getIdColumn(),
                PersonTableUtil.getFirstNameColumn(),
                PersonTableUtil.getLastNameColumn(),
                PersonTableUtil.getBirthDateColumn()
        );

        // Crea el panel para ingresar nuevos datos
        GridPane newDataPane = this.getNewPersonDataPane();
        // Botón para restaurar filas
        Button restoreBtn = new Button("Restore Rows");
        Tooltip tipReset = new Tooltip("Reiniciar Datos");
        restoreBtn.setTooltip(tipReset);
        restoreBtn.setOnAction(e -> restoreRows());

        // Botón para eliminar filas seleccionadas
        Button deleteBtn = new Button("Delete Selected Rows");
        Tooltip tipBorrar = new Tooltip("Borrar Datos Seleccionados");
        deleteBtn.setTooltip(tipBorrar);
        deleteBtn.setOnAction(e -> deleteSelectedRows());

        // Configura el contenedor principal de la interfaz
        VBox root = new VBox(newDataPane, new HBox(restoreBtn, deleteBtn), table);
        root.setSpacing(5);
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        // Configura la escena y muestra la ventana
        Scene scene = new Scene(root);
        stage.setMinHeight(500); // Establece altura mínima
        stage.setMinWidth(400); // Establece anchura mínima
        stage.setMaxWidth(400); // Establece anchura máxima
        stage.setScene(scene);
        stage.setTitle("Adding/Deleting Rows in a TableViews"); // Título de la ventana
        stage.show(); // Muestra la ventana
    }

    /**
     * Crea y devuelve un panel de entrada de datos para agregar una nueva persona.
     *
     * @return Un GridPane con campos de entrada para el nombre, apellido y fecha de nacimiento.
     */
    public GridPane getNewPersonDataPane() {
        GridPane pane = new GridPane(); // Crea un nuevo GridPane
        pane.setHgap(10); // Espaciado horizontal
        pane.setVgap(5); // Espaciado vertical
        pane.addRow(0, new Label("First Name:"), fNameField); // Agrega fila para el nombre
        pane.addRow(1, new Label("Last Name:"), lNameField); // Agrega fila para el apellido
        pane.addRow(2, new Label("Birth Date:"), dobField); // Agrega fila para la fecha de nacimiento

        // Botón para agregar una nueva persona
        Button addBtn = new Button("Add");
        Tooltip tipAniade = new Tooltip("Añadir Fila");
        addBtn.setTooltip(tipAniade);
        addBtn.setOnAction(e -> addPerson()); // Acción al hacer clic
        pane.add(addBtn, 2, 0); // Agrega el botón al panel

        return pane; // Devuelve el panel
    }

    /**
     * Elimina las filas seleccionadas de la tabla.
     */
    public void deleteSelectedRows() {
        TableViewSelectionModel<Person> tsm = table.getSelectionModel(); // Obtiene el modelo de selección
        if (tsm.isEmpty()) { // Verifica si hay filas seleccionadas
            System.out.println("Please select a row to delete."); // Mensaje si no hay selección
            return; // Sale del metodo
        }
        ObservableList<Integer> list = tsm.getSelectedIndices(); // Obtiene los índices seleccionados
        Integer[] selectedIndices = list.toArray(new Integer[0]); // Convierte a array
        Arrays.sort(selectedIndices); // Ordena los índices en orden descendente
        for (int i = selectedIndices.length - 1; i >= 0; i--) { // Recorre los índices en orden inverso
            tsm.clearSelection(selectedIndices[i]); // Limpia la selección
            table.getItems().remove(selectedIndices[i].intValue()); // Elimina el elemento de la tabla
        }
    }

    /**
     * Restaura todas las filas en la tabla a su estado original.
     */
    public void restoreRows() {
        table.getItems().clear(); // Limpia la tabla
        table.getItems().addAll(PersonTableUtil.getPersonList()); // Restaura la lista original
    }

    /**
     * Crea un objeto Person a partir de los datos ingresados en los campos.
     *
     * @return Un objeto Person con los datos ingresados.
     */
    public Person getPerson() {
        return new Person(fNameField.getText(), lNameField.getText(), dobField.getValue()); // Crea un nuevo objeto Person
    }

    /**
     * Agrega una nueva persona a la tabla.
     * Muestra un mensaje de error si algún campo está vacío.
     */
    public void addPerson() {
        // Verifica que todos los campos estén llenos
        if (fNameField.getText().isEmpty() || lNameField.getText().isEmpty() || dobField.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR); // Crea una alerta de error
            alert.setTitle("ERROR");
            alert.setHeaderText(null); // Opcional, puedes añadir un encabezado si lo necesitas
            alert.setContentText("Rellena todos los campos para añadir una fila"); // Mensaje de error
            alert.showAndWait(); // Muestra la alerta
        } else {
            Person p = getPerson(); // Obtiene el objeto Person
            table.getItems().add(p); // Agrega a la tabla
            clearFields(); // Limpia los campos
        }
    }

    /**
     * Limpia los campos de entrada de datos.
     */
    public void clearFields() {
        fNameField.setText(null); // Limpia el campo de nombre
        lNameField.setText(null); // Limpia el campo de apellido
        dobField.setValue(null); // Limpia el campo de fecha de nacimiento
    }
}
