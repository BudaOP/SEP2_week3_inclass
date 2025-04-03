module org.example.sep2_week3_inclass {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.sep2_week3_inclass to javafx.fxml;
    exports org.example.sep2_week3_inclass;
}
