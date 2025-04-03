module org.example.sep2_week3_inclass {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.sep2_week3_inclass to javafx.fxml;
    exports org.example.sep2_week3_inclass;
}