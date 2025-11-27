package com.example.budget.repository;

import com.example.budget.domain.Budget;
import com.example.budget.repository.BudgetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BudgetRepositoryTest {

    @Autowired
    private BudgetRepository repository;

    @Test
    void testFindByOwnerCategoryMonth() {
        LocalDate monthStart = LocalDate.of(2025, 1, 1);

        Budget b = Budget.builder()
                .ownerId("u1")
                .category("Food")
                .monthStart(monthStart)
                .amount(new BigDecimal("200"))
                .build();

        repository.save(b);

        Optional<Budget> result =
                repository.findByOwnerIdAndCategoryAndMonthStart("u1", "Food", monthStart);

        assertTrue(result.isPresent());
        assertEquals("u1", result.get().getOwnerId());
        assertEquals("Food", result.get().getCategory());
        assertEquals(monthStart, result.get().getMonthStart());
    }
}

