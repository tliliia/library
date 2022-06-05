package ru.maxima.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Enrollment extends BaseEntity {
    private Long productId;
    private Long accountId;
    @Enumerated(EnumType.STRING)
    private EAction lastAction;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<EHistory> history;
}
