package com.example.budget.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {

    @Test
    void testBuilderAndGetters() {
        LocalDate monthStart = LocalDate.of(2025, 1, 1);
        Budget b = Budget.builder()
                .id(1L)
                .ownerId("user1")
                .category("Food")
                .monthStart(monthStart)
                .amount(new BigDecimal("500.00"))
                .build();

        assertEquals(1L, b.getId());
        assertEquals("user1", b.getOwnerId());
        assertEquals("Food", b.getCategory());
        assertEquals(monthStart, b.getMonthStart());
        assertEquals(new BigDecimal("500.00"), b.getAmount());
    }

    @Test
    void testSetters() {
        Budget b = new Budget();

        b.setOwnerId("abc");
        b.setCategory("Travel");
        b.setAmount(new BigDecimal("100"));
        b.setMonthStart(LocalDate.of(2024, 5, 1));

        assertEquals("abc", b.getOwnerId());
        assertEquals("Travel", b.getCategory());
        assertEquals(new BigDecimal("100"), b.getAmount());
    }
}
