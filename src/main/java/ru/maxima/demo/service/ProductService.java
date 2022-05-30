package ru.maxima.demo.service;


import ru.maxima.demo.mapper.ProductDto;
import ru.maxima.demo.mapper.pagedDto.ProductPage;

public interface ProductService {

    ProductDto add(ProductDto product);

    ProductPage getAll(int page);

    ProductPage search(String text, int page);
}
