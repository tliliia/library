package ru.maxima.demo.dto.pagedDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DtoPage<T> {
    private Integer pageSize;
    private Integer totalPage;

    private List<T> data;
}
