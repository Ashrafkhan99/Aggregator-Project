package com.example.Aggregator.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "verification_tokens")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    public Long getId() {
        return id;
    }

    public VerificationToken(Long id, String token, User user, Date expirationDate) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    public VerificationToken() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = (Date) expirationDate;
    }
}
