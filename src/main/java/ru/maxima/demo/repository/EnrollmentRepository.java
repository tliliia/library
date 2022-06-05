package ru.maxima.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.maxima.demo.model.EHistory;
import ru.maxima.demo.model.Enrollment;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;


public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Enrollment findOneByAccountIdAndProductId(Long accountId, Long productId);

    Page<Enrollment> getByAccountId(Long accountId, Pageable pageable);

    List<Enrollment> findAll();

    @Query(nativeQuery = true, value = "select * from EHISTORY h " +
            "join ENROLLMENT e on e.ID = h.RELATION_ID " +
            "where date between :startDate and :endDate")
    List<Enrollment> getAllByDateBetween(Instant startDate, Instant endDate);
}
