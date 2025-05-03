package com.example.Aggregator.DTO;

import com.example.Aggregator.Entity.Preference;

import java.util.Set;

public class UserDto {
    private String name;
    private String role;
    private String password;
    private boolean isActive;
    private Set<Preference> preferences;

    public UserDto() {
    }

    public UserDto(String name, String role, String password, boolean isActive, Set<Preference> preferences) {
        this.name = name;
        this.role = role;
        this.password = password;
        this.isActive = isActive;
        this.preferences = preferences;
    }

    public Set<Preference> getPreferences() {
        return preferences;
    }

    public void setPreferences(Set<Preference> preferences) {
        this.preferences = preferences;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
