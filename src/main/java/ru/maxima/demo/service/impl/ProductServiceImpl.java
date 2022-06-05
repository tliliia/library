package ru.maxima.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.maxima.demo.exceptions.ProductNotAvailableException;
import ru.maxima.demo.exceptions.ProductNotFoundException;
import ru.maxima.demo.dto.ProductDto;
import ru.maxima.demo.dto.pagedDto.ProductPage;
import ru.maxima.demo.model.Product;
import ru.maxima.demo.repository.ProductRepository;
import ru.maxima.demo.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final static int DEFAULT_PAGE_SIZE = 5;

    private final ProductRepository productRepository;

    @Override
    public ProductDto add(ProductDto dto) {
        Product product = Product.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .build();

        productRepository.save(product);

        return ProductDto.from(product);
    }

    @Override
    public ProductDto reduceAvailableAmount(Long productId) {
        Product entityToUpdate = productRepository.getReferenceById(productId);
        if (entityToUpdate == null) {
            throw new ProductNotFoundException();
        }
        if (entityToUpdate.getAvailableAmount() <= 0) {//synchronized
            throw new ProductNotAvailableException();
        } else {
            entityToUpdate.setAvailableAmount(entityToUpdate.getAvailableAmount() - 1);//synchronized

            Product updated = productRepository.save(entityToUpdate);
            return ProductDto.from(updated);
        }
    }

    @Override
    public ProductDto enlargeAvailableAmount(Long productId) {
        Product entityToUpdate = productRepository.getReferenceById(productId);
        entityToUpdate.setAvailableAmount(entityToUpdate.getAvailableAmount() + 1);//synchronized
        Product updated = productRepository.save(entityToUpdate);
        return ProductDto.from(updated);
    }

    @Override
    public ProductPage getAll(int page) {
        PageRequest pageRequest = PageRequest.of(page, DEFAULT_PAGE_SIZE, Sort.by("title"));
        Page<Product> pageResult = productRepository.findAll(pageRequest);
        if (pageResult.getContent().size() == 0) {
            throw new ProductNotFoundException();
        }
        return ProductPage.builder()
                .data(ProductDto.fromList(pageResult.getContent()))
                .pageSize(pageResult.getSize())
                .totalPage(pageResult.getTotalPages())
                .build();

    }

    @Override
    public ProductPage search(String text, int page) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException("Строка для поиска не заполнена");
        }
        PageRequest pageRequest = PageRequest.of(page, DEFAULT_PAGE_SIZE, Sort.by("title"));
        Page<Product> pageResult = productRepository.findByTitleContainingOrDescriptionContaining(text, text, pageRequest);
        if (pageResult.getContent().size() == 0) {
            throw new ProductNotFoundException();
        }
        return ProductPage.builder()
                .data(ProductDto.fromList(pageResult.getContent()))
                .pageSize(pageResult.getSize())
                .totalPage(pageResult.getTotalPages())
                .build();
    }

}
