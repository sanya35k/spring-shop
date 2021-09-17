package com.konstantinov.springshop.model;

public enum Role {
    USER("Role user"),
    ADMIN("Role admin");

    private String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}