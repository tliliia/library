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
import ru.maxima.demo.model.EAction;
import ru.maxima.demo.model.EHistory;
import ru.maxima.demo.model.Enrollment;
import ru.maxima.demo.repository.AccountRepository;
import ru.maxima.demo.repository.EnrollmentRepository;
import ru.maxima.demo.service.EnrollmentService;
import ru.maxima.demo.service.ProductService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private static final int DEFAULT_PAGE_SIZE = 5;

    private final EnrollmentRepository enrollmentRepository;
    private final AccountRepository accountRepository;
    private final ProductService productService;

    @Override
    public void userTakeBook(Long accountId, Long bookId) {
        productService.reduceAvailableAmount(bookId);
        Enrollment result = updateEnrollment(accountId, bookId, EAction.TAKE);
    }

    @Override
    public void userReturnBook(Long accountId, Long bookId) {
        productService.enlargeAvailableAmount(bookId);
        Enrollment result = updateEnrollment(accountId, bookId, EAction.RETURN);
    }

    private synchronized Enrollment updateEnrollment(Long accountId, Long bookId, EAction action) {
        Enrollment enrollment = enrollmentRepository.findOneByAccountIdAndProductId(accountId, bookId);
        if (enrollment == null) {
            enrollment = Enrollment.builder()
                    .productId(bookId)
                    .accountId(accountId)
                    .history(new ArrayList<>())
                    .build();
        }
        enrollment.setLastAction(action);
        enrollment = enrollmentRepository.save(enrollment);//чтобы появился id
        enrollment = updateHistory(enrollment);
        return  enrollment;
    }

    private Enrollment updateHistory(Enrollment enrollment) {
        enrollment.getHistory().add(
                EHistory.builder()
                        .relationId(enrollment.getId())
                        .action(enrollment.getLastAction())
                        .date(Instant.now())
                        .build());
        return enrollmentRepository.save(enrollment);
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
    public List<AccountDto> usersWhoTakeBook(Long productId) {
        List<Account> result = accountRepository.getAccounstWhoTakeBook(productId);
        return AccountDto.fromList(result);
    }

    @Override
    public List<EnrollmentDto> getHistoryByDateBetween(Instant startDate, Instant endDate) {
        return EnrollmentDto.fromList(enrollmentRepository.getAllByDateBetween(startDate, endDate));
    }

    @Override
    public List<EnrollmentDto> getAllHistory() {
        return EnrollmentDto.fromList(enrollmentRepository.findAll());
    }
}
