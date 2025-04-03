package org.example.sep2_week3_inclass;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizedApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("org.example.sep2_week3_inclass.messages", Locale.ENGLISH);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"), bundle);
        Scene scene = new Scene(loader.load(), 420, 520);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Ivan Budanov");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
