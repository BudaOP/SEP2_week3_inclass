package org.example.sep2_week3_inclass;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBHelper {
    private static final String URL = "jdbc:mariadb://localhost:3306/sep2_week3_inclass";
    private static final String USER = "root";
    private static final String PASS = "KierukkaKupariNöö7!";

    public static String getTranslation(String key, String langCode) {
        String sql = "SELECT translation_text FROM translations WHERE Key_name = ? AND Language_code = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, key);
            stmt.setString(2, langCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("translation_text");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "(not found)";
    }

    public static void insertTranslation(String key, String langCode, String translation) {
        String sql = "INSERT INTO translations (Key_name, Language_code, translation_text) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE translation_text = VALUES(translation_text)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, key);
            stmt.setString(2, langCode);
            stmt.setString(3, translation);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTranslation(String key, String langCode) {
        String sql = "DELETE FROM translations WHERE Key_name = ? AND Language_code = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, key);
            stmt.setString(2, langCode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Map<String, String> getAllTranslations(String langCode) {
        Map<String, String> translations = new HashMap<>();
        String sql = "SELECT Key_name, translation_text FROM translations WHERE Language_code = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, langCode);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                translations.put(rs.getString("Key_name"), rs.getString("translation_text"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return translations;
    }
}
