package ru.maxima.demo.dto.pagedDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maxima.demo.dto.EnrollmentDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentPage {
    private Integer pageSize;
    private Integer totalPage;

    private List<EnrollmentDto> data;
}
