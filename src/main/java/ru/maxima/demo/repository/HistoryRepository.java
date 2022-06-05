package ru.maxima.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxima.demo.model.EHistory;
import ru.maxima.demo.model.Enrollment;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository extends JpaRepository<EHistory, Long> {

}
