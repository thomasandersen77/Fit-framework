package com.github.fit.examples.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Data
public class BaseEntity {
    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @Setter(AccessLevel.NONE)
    private int id;

    @NonNull
    @Column(name = "Fornanvn")
    private String fornavn;
}
