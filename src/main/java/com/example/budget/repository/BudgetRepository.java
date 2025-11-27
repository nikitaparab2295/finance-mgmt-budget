package com.example.budget.repository;

import com.example.budget.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByOwnerIdAndCategoryAndMonthStart(String ownerId, String
            category, LocalDate monthStart);
}
