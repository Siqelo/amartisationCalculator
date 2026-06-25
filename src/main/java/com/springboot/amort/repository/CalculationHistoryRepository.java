package com.springboot.amort.repository;

import com.springboot.amort.entity.CalculationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationHistoryRepository extends JpaRepository<CalculationHistory, Long> {
}
