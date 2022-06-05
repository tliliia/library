package ru.maxima.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maxima.demo.model.EAction;
import ru.maxima.demo.model.Enrollment;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentDto {

    private String accountEmail;
    private String productTitle;
    private EAction lastAction;

    private List<HistoryDto> history;

    public static EnrollmentDto from(Enrollment entity) {
        return EnrollmentDto.builder()
                .productTitle(entity.getProductId().toString())
                .accountEmail(entity.getAccountId().toString())
                .lastAction(entity.getLastAction())
                .history(HistoryDto.fromList(entity.getHistory()))
                .build();
    }

    public static List<EnrollmentDto> fromList(List<Enrollment> entities) {
        return entities.stream()
                .map(EnrollmentDto::from)
                .collect(Collectors.toList());
    }
}
