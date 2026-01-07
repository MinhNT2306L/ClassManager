package org.example.classmanager.model;

import java.sql.Timestamp;

public class Challenge {
    private Long id;
    private String hint;
    private String filePath;
    private Timestamp createdAt;

    public Challenge() {
    }

    public Challenge(Long id, String hint, String filePath, Timestamp createdAt) {
        this.id = id;
        this.hint = hint;
        this.filePath = filePath;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
