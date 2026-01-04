package org.example.classmanager.model;

import java.sql.Timestamp;

public class Assignment {
    private Long id;
    private String title;
    private String description;
    private String filePath;
    private Long teacherId;
    private Timestamp createdAt;

    public Assignment() {
    }

    public Assignment(Long id, String title, String description, String filePath, Long teacherId, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.teacherId = teacherId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
