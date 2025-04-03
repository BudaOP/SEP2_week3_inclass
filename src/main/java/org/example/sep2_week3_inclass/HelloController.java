package org.example.sep2_week3_inclass;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {
    @FXML
    private ComboBox<String> languageBox;
    @FXML
    private Label greetingLabel;
    @FXML
    private Button fetchButton;
    @FXML
    private Label dbResultLabel;
    @FXML
    private TextField keyField;
    @FXML
    private TextField translationField;
    @FXML
    private Button saveButton;
    @FXML
    private ListView<String> jobListView;
    @FXML
    private Button deleteButton;


    private static final Map<String, Locale> supportedLocales = Map.of(
            "English", new Locale("en", "US"),
            "French", new Locale("fr", "FR"),
            "Spanish", new Locale("es", "ES"),
            "Chinese", new Locale("zh", "CN")
    );

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        languageBox.getItems().addAll(supportedLocales.keySet());
        languageBox.getSelectionModel().selectFirst();
        languageBox.setOnAction(e -> {
            updateLanguage();
            refreshJobList();
        });

        updateLanguage();
        refreshJobList();

        fetchButton.setOnAction(e -> {
            String langCode = getLanguageCode(languageBox.getValue());
            String result = DBHelper.getTranslation("job.title", langCode);
            dbResultLabel.setText(result);
        });

        saveButton.setOnAction(e -> {
            String langCode = getLanguageCode(languageBox.getValue());
            String key = keyField.getText().trim();
            String value = translationField.getText().trim();
            if (!key.isEmpty() && !value.isEmpty()) {
                DBHelper.insertTranslation(key, langCode, value);
                keyField.clear();
                translationField.clear();
                refreshJobList();
            }
        });

        deleteButton.setOnAction(e -> {
            String selected = jobListView.getSelectionModel().getSelectedItem();
            if (selected != null && selected.contains(":")) {
                String key = selected.split(":")[0].trim();
                String langCode = getLanguageCode(languageBox.getValue());
                DBHelper.deleteTranslation(key, langCode);
                refreshJobList();
            }
        });

    }

    private void refreshJobList() {
        jobListView.getItems().clear();
        String langCode = getLanguageCode(languageBox.getValue());
        Map<String, String> all = DBHelper.getAllTranslations(langCode);
        for (var entry : all.entrySet()) {
            jobListView.getItems().add(entry.getKey() + ": " + entry.getValue());
        }
    }

    private String getLanguageCode(String language) {
        return switch (language) {
            case "English" -> "en_US";
            case "French" -> "fr_FR";
            case "Spanish" -> "es_ES";
            case "Chinese" -> "zh_CN";
            default -> "en_US";
        };
    }

    private void updateLanguage() {
        String selected = languageBox.getValue();
        Locale locale = supportedLocales.get(selected);
        ResourceBundle bundle = ResourceBundle.getBundle("org.example.sep2_week3_inclass.messages", locale);
        greetingLabel.setText(bundle.getString("label.greeting"));
        fetchButton.setText(bundle.getString("button.fetch"));
        saveButton.setText(bundle.getString("button.save"));
        deleteButton.setText(bundle.getString("button.delete"));
        keyField.setPromptText(bundle.getString("placeholder.key"));
        translationField.setPromptText(bundle.getString("placeholder.translation"));
    }

}
