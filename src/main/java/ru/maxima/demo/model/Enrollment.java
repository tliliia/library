package ru.maxima.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Enrollment extends BaseEntity {
    private Long productId;
    private Long accountId;
    @Enumerated(EnumType.STRING)
    private Status action;
    private Instant date;
}
