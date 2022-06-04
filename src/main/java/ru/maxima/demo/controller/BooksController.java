package ru.maxima.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maxima.demo.dto.ProductDto;
import ru.maxima.demo.dto.pagedDto.ProductPage;
import ru.maxima.demo.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BooksController {
    private final ProductService productService;

    @Operation(summary = "Получение списка изданий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с изданиями", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductPage.class))
            }),
            @ApiResponse(responseCode = "404", description = "Издание не найдено")
    })
    @GetMapping
    public ResponseEntity<ProductPage> getAllBooks(
            @Parameter(description = "Номер страницы") @RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(productService.getAll(page));
    }


    @Operation(summary = "Создание издания")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Созданное издание", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class))
            })
    })
    @PostMapping
    public ResponseEntity<ProductDto> addBook(@RequestBody ProductDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.add(dto));
    }

    @Operation(summary = "Поиск по строке (часть названия или описания)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с изданиями", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductPage.class))
            }),
            @ApiResponse(responseCode = "404", description = "Издание, удовлетворяющее поисковой строке, не найдено")
    })
    @GetMapping("/search")
    public ResponseEntity<ProductPage> searchBooksByText(
            @Parameter(description = "Строка поиска") @RequestParam(value = "q") String text,
            @Parameter(description = "Номер страницы") @RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(productService.search(text, page));
    }

}
