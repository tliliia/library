package ru.maxima.demo.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maxima.demo.model.Product;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private String title;
    private String description;
    private Integer amount;
    private Integer availableAmount;

    public static ProductDto from(Product entity) {
        return ProductDto.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .amount(entity.getAmount())
                .availableAmount(entity.getAvailableAmount())
                .build();
    }

    public static List<ProductDto> fromList(List<Product> entities) {
        return entities.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }
}
