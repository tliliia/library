package ru.maxima.demo.service;


import ru.maxima.demo.dto.AccountDto;
import ru.maxima.demo.dto.EnrollmentDto;
import ru.maxima.demo.dto.pagedDto.EnrollmentPage;

import java.awt.print.Pageable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface EnrollmentService {
    //    фиксирование факта передачи книги посетителю;
    void userTakeBook(Long accountId, Long bookId);

    //    фиксирование факта получения книги от посетителя;
    void userReturnBook(Long accountId, Long bookId);

    //    получение полной истории операций конкретного посетителя.
    EnrollmentPage getUserHistory(Long accountId, int page);

    //    получение списка посетителей, имеющих на руках конкретную книгу;
    List<AccountDto> usersWhoTakeBook(Long productId);

    //    возможность получить какую-либо статистику по книгам или посетителям за задаваемый в запросе промежуток времени;
    List<EnrollmentDto> getHistoryByDateBetween(Instant startDate, Instant endDate);

    List<EnrollmentDto> getAllHistory();

}
