package ru.maxima.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maxima.demo.service.EnrollmentService;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {
    private final EnrollmentService service;

    @Operation(summary = "Фиксирование факта передачи книги посетителю")
    @PostMapping("/take")
    public ResponseEntity userTakeBook(@RequestParam Long userId,
                                       @RequestParam Long bookId) {
        service.userTakeBook(userId, bookId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @Operation(summary = "Фиксирование факта получения книги от посетителя")
    @PostMapping("/return")
    public ResponseEntity userReturnBook(@RequestParam Long userId,
                                         @RequestParam Long bookId) {
        service.userReturnBook(userId, bookId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @Operation(summary = "Получение полной истории операций конкретного посетителя")
    @GetMapping("/users/{userId}")
    public ResponseEntity getUserHistory(
            @PathVariable("userId") Long userId,
            @Parameter(description = "Номер страницы") @RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(service.getUserHistory(userId, page));
    }

    @Operation(summary = "Получение списка посетителей, имеющих на руках конкретную книгу")
    @GetMapping("/books/{bookId}")
    public ResponseEntity usersHoldingBook(
            @PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok(service.usersWhoTakeBook(bookId));
    }

    @Operation(summary = "Получить историю за задаваемый в запросе промежуток времени")
    @GetMapping("")
    public ResponseEntity getMetrics(@RequestParam String startDate,
                                     @RequestParam String endDate) {
        Instant start = Instant.parse(startDate);
        Instant end = Instant.parse(endDate);
        return ResponseEntity.ok(service.getHistoryByDateBetween(start, end));
    }

}
