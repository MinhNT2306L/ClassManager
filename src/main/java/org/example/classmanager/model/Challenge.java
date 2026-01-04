package org.example.classmanager.model;

import java.sql.Timestamp;

public class Challenge {
    private Long id;
    private String hint;
    private Timestamp createdAt;

    public Challenge() {
    }

    public Challenge(Long id, String hint, Timestamp createdAt) {
        this.id = id;
        this.hint = hint;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
