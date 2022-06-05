package ru.maxima.demo.dto;

import lombok.Builder;
import lombok.Data;
import ru.maxima.demo.model.EHistory;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class HistoryDto {
    private Long id;

    private Instant date;
    private String action;

    private static HistoryDto from(EHistory entity) {
        return  HistoryDto.builder()
                .id(entity.getId())
                .action(entity.getAction().toString())
                .date(entity.getDate())
                .build();
    }

    public static List<HistoryDto> fromList(List<EHistory> entities) {
        return entities.stream()
                .map(HistoryDto::from)
                .collect(Collectors.toList());
    }

}
