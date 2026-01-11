package org.example.classmanager.dao;

import org.example.classmanager.model.Challenge;
import org.example.classmanager.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChallengeDAO {

    public void createChallenge(Challenge challenge) {
        String query = "INSERT INTO challenges (hint, file_path) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, challenge.getHint());
            stmt.setString(2, challenge.getFilePath());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Challenge findById(Long id) {
        String query = "SELECT * FROM challenges WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Challenge(
                        rs.getLong("id"),
                        rs.getString("hint"),
                        rs.getString("file_path"),
                        rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Challenge> findAll() {
        List<Challenge> list = new ArrayList<>();
        String query = "SELECT * FROM challenges ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Challenge(
                        rs.getLong("id"),
                        rs.getString("hint"),
                        rs.getString("file_path"),
                        rs.getTimestamp("created_at")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
