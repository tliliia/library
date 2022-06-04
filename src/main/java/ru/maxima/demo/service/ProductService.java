package ru.maxima.demo.service;


import ru.maxima.demo.dto.ProductDto;
import ru.maxima.demo.dto.pagedDto.ProductPage;

public interface ProductService {

    ProductDto add(ProductDto product);

    ProductDto reduceAvailableAmount(Long productId);

    ProductDto enlargeAvailableAmount(Long productId);

    ProductPage getAll(int page);

    ProductPage search(String text, int page);
}
