package ru.maxima.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxima.demo.model.Enrollment;

import java.time.LocalDate;
import java.util.List;


public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Page<Enrollment> getByAccountId(Long accountId, Pageable pageable);

    List<Enrollment> getAllByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Enrollment> findAll();
}
