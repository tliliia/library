package ru.maxima.demo.mapper.pagedDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maxima.demo.mapper.ProductDto;

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