module es.aritzherrero.pantallarellenardatos {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.aritzherrero.pantallarellenardatos to javafx.fxml;
    exports es.aritzherrero.pantallarellenardatos;
}