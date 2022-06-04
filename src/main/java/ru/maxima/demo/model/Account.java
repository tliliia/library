package ru.maxima.demo.model;

import lombok.Data;

import javax.persistence.Entity;


@Data
@Entity
public class Account extends BaseEntity {

    private String email;
}
