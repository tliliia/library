package ru.maxima.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Product extends BaseEntity {

    private String title;
    private String description;
    private Integer amount;
    private Integer availableAmount;
}
