package ru.maxima.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maxima.demo.dto.AccountDto;
import ru.maxima.demo.dto.EnrollmentDto;
import ru.maxima.demo.dto.ProductDto;
import ru.maxima.demo.dto.pagedDto.EnrollmentPage;
import ru.maxima.demo.service.EnrollmentService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
public class HistoryController {
    private final EnrollmentService service;

    @Operation(summary = "фиксирование факта передачи книги посетителю")
    @PostMapping("/take")
    public ResponseEntity userTakeBook(@RequestParam AccountDto user,
                                       @RequestParam ProductDto book) {
        service.userTakeBook(user.getId(), book.getId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @Operation(summary = "фиксирование факта получения книги от посетителя")
    @PostMapping("/return")
    public ResponseEntity userReturnBook(@RequestParam AccountDto user,
                                         @RequestParam ProductDto book) {
        service.userReturnBook(user.getId(), book.getId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @Operation(summary = "получение полной истории операций конкретного посетителя")
    @GetMapping("/history/{userId}")
    public ResponseEntity getUserHistory(
            @PathVariable("userId") Long userId,
            @Parameter(description = "Номер страницы") @RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(service.getUserHistory(userId, page));
    }

    @Operation(summary = "получение списка посетителей, имеющих на руках конкретную книгу")
    @GetMapping("/history/{bookId}")
    public ResponseEntity usersHoldingBook(
            @PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok(service.usersHoldingBook(bookId));
    }

    @Operation(summary = "получить какую-либо статистику по книгам или посетителям за задаваемый в запросе промежуток времени")
    @GetMapping("/history")
    public ResponseEntity getMetrics(@RequestParam Instant startDate,
                                     @RequestParam Instant endDate) {
        return ResponseEntity.ok(service.getMetrics(null, null));
    }

    @GetMapping("/test")
    public void doTest() {
        Long user1 = 1L;
        Long user2 = 2L;
        Long bookId = 1L;
        service.userTakeBook(user1, bookId);
        service.userTakeBook(user2, bookId);

        service.getAllHistory();

        try {
            service.userTakeBook(user1, bookId);
        } catch (Exception e) {}
        List<AccountDto> r = service.usersHoldingBook(bookId);

        service.userReturnBook(user1, bookId);
        service.userReturnBook(user2, bookId);

        EnrollmentPage h = service.getUserHistory(user1, 0);
        h = service.getUserHistory(user2, 0);

        r = service.usersHoldingBook(bookId);
    }

}
