package org.example.classmanager.dao;

import org.example.classmanager.model.Assignment;
import org.example.classmanager.model.Submission;
import org.example.classmanager.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {

    public void createAssignment(Assignment assignment) {
        String query = "INSERT INTO assignments (title, description, file_path, teacher_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, assignment.getTitle());
            stmt.setString(2, assignment.getDescription());
            stmt.setString(3, assignment.getFilePath());
            stmt.setLong(4, assignment.getTeacherId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Assignment> findAll() {
        List<Assignment> list = new ArrayList<>();
        String query = "SELECT * FROM assignments ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(mapAssignment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Assignment findById(Long id) {
        String query = "SELECT * FROM assignments WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapAssignment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Submission Methods

    public void submitAssignment(Submission submission) {
        String query = "INSERT INTO submissions (assignment_id, student_id, file_path) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, submission.getAssignmentId());
            stmt.setLong(2, submission.getStudentId());
            stmt.setString(3, submission.getFilePath());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Submission> findSubmissionsByAssignmentId(Long assignmentId) {
        List<Submission> list = new ArrayList<>();
        String query = "SELECT * FROM submissions WHERE assignment_id = ? ORDER BY submitted_at DESC";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, assignmentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapSubmission(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Assignment mapAssignment(ResultSet rs) throws SQLException {
        return new Assignment(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("file_path"),
                rs.getLong("teacher_id"),
                rs.getTimestamp("created_at"));
    }

    private Submission mapSubmission(ResultSet rs) throws SQLException {
        return new Submission(
                rs.getLong("id"),
                rs.getLong("assignment_id"),
                rs.getLong("student_id"),
                rs.getString("file_path"),
                rs.getTimestamp("submitted_at"));
    }
}
