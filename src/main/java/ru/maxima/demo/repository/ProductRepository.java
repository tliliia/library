package ru.maxima.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxima.demo.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    Page<Product> findByTitleContainingOrDescriptionContaining(String titleQ, String  descQ, Pageable pageable);
}
