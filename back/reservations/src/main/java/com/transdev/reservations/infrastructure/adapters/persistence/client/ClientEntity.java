package com.transdev.reservations.infrastructure.adapters.persistence.client;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_seq_gen")
    @SequenceGenerator(name = "clients_seq_gen", sequenceName = "clients_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}