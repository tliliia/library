package ru.maxima.demo.dto.pagedDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maxima.demo.dto.ProductDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPage {
    private Integer pageSize;
    private Integer totalPage;

    private List<ProductDto> data;
}