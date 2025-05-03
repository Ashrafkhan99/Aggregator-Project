package com.example.Aggregator.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String role;

    private String password;

    private boolean isActive;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Preference> preferences = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favorite> favorites = new HashSet<>();

    public User() {
    }

    public User(Long id, String name, String role, String password, boolean isActive, Set<Preference> preferences) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
        this.isActive = isActive;
        this.preferences = preferences;
    }

    public void setPreferences(Set<Preference> preferences) {
        this.preferences = preferences;
    }

    public Set<Preference> getPreferences() {
        return preferences;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public Set<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Favorite> favorites) {
        this.favorites = favorites;
    }
}
