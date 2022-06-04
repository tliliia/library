package ru.maxima.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product extends BaseEntity {

    private String title;
    private String author;
    @Column(columnDefinition="varchar(1500)")
    private String description;
    private Integer amount;
    private Integer availableAmount;

}
