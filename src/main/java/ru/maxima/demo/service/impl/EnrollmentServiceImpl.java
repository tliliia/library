package ru.maxima.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.maxima.demo.exceptions.EnrollmentNotFoundException;
import ru.maxima.demo.dto.AccountDto;
import ru.maxima.demo.dto.EnrollmentDto;
import ru.maxima.demo.dto.pagedDto.EnrollmentPage;
import ru.maxima.demo.model.Account;
import ru.maxima.demo.model.Status;
import ru.maxima.demo.model.Enrollment;
import ru.maxima.demo.repository.AccountRepository;
import ru.maxima.demo.repository.EnrollmentRepository;
import ru.maxima.demo.service.EnrollmentService;
import ru.maxima.demo.service.ProductService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    public static final Calendar tzUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    private static final int DEFAULT_PAGE_SIZE = 5;

    private final EnrollmentRepository enrollmentRepository;
    private final AccountRepository accountRepository;
    private final ProductService productService;

    @Override
    public void userTakeBook(Long accountId, Long bookId) {
        productService.reduceAvailableAmount(bookId);
        Enrollment enrollment = Enrollment.builder()
                .date(Instant.now())
                .action(Status.TAKE)
                .productId(bookId)
                .accountId(accountId)
                .build();
        enrollmentRepository.save(enrollment);
    }

    @Override
    public void userReturnBook(Long accountId, Long bookId) {
        productService.enlargeAvailableAmount(bookId);
        Enrollment enrollment = Enrollment.builder()
                .date(Instant.now())
                .action(Status.RETURN)
                .productId(bookId)
                .accountId(accountId)
                .build();
        enrollmentRepository.save(enrollment);
    }

    @Override
    public EnrollmentPage getUserHistory(Long accountId, int page) {
        PageRequest pageRequest = PageRequest.of(page, DEFAULT_PAGE_SIZE, Sort.by("id"));
        Page<Enrollment> pageResult = enrollmentRepository.getByAccountId(accountId, pageRequest);
        if (pageResult.getContent().size() == 0) {
            throw new EnrollmentNotFoundException();
        }
        return EnrollmentPage.builder()
                .data(EnrollmentDto.fromList(pageResult.getContent()))
                .pageSize(pageResult.getSize())
                .totalPage(pageResult.getTotalPages())
                .build();
    }

    @Override
    public List<AccountDto> usersHoldingBook(Long productId) {
        List<Account> result = accountRepository.getAccounstHoldingBook(productId);
        return AccountDto.fromList(result);
    }

    @Override
    public List<EnrollmentDto> getMetrics(Instant startDate, Instant endDate) {
//        return EnrollmentDto.fromList(enrollmentRepository.getAllByDateBetween(startDate, endDate));
        return new ArrayList<>();
    }

    @Override
    public List<EnrollmentDto> getAllHistory() {
        return EnrollmentDto.fromList(enrollmentRepository.findAll());
    }
}
