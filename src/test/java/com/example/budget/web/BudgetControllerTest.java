package com.example.budget.web;

import com.example.budget.domain.Budget;
import com.example.budget.repository.BudgetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(controllers = BudgetController.class)
class BudgetControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BudgetRepository repo;

    @Test
    void testCreateBudgetSuccess() throws Exception {
        Budget saved = Budget.builder()
                .id(1L)
                .ownerId("u1")
                .category("Food")
                .monthStart(LocalDate.of(2025, 1, 1))
                .amount(new BigDecimal("100"))
                .build();

        Mockito.when(repo.save(any())).thenReturn(saved);

        mvc.perform(post("/api/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "ownerId": "u1",
                            "category": "Food",
                            "month": "2025-01",
                            "amount": 100
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/budgets/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.ownerId").value("u1"))
                .andExpect(jsonPath("$.category").value("Food"));
    }

    @Test
    void testCreateBudgetInvalidMonth() throws Exception {
        mvc.perform(post("/api/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "ownerId": "u1",
                            "category": "Food",
                            "month": "invalid",
                            "amount": 100
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetByIdFound() throws Exception {
        Budget b = Budget.builder()
                .id(10L)
                .ownerId("u1")
                .category("Food")
                .monthStart(LocalDate.now())
                .amount(new BigDecimal("100"))
                .build();

        Mockito.when(repo.findById(10L)).thenReturn(Optional.of(b));

        mvc.perform(get("/api/budgets/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        Mockito.when(repo.findById(99L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/budgets/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchFound() throws Exception {
        LocalDate monthStart = LocalDate.of(2025, 1, 1);

        Budget b = Budget.builder()
                .id(1L)
                .ownerId("u1")
                .category("Food")
                .monthStart(monthStart)
                .amount(new BigDecimal("100"))
                .build();

        Mockito.when(repo.findByOwnerIdAndCategoryAndMonthStart("u1", "Food", monthStart))
                .thenReturn(Optional.of(b));

        mvc.perform(get("/api/budgets/search")
                        .param("ownerId", "u1")
                        .param("category", "Food")
                        .param("month", "2025-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testSearchInvalidMonth() throws Exception {
        mvc.perform(get("/api/budgets/search")
                        .param("ownerId", "u1")
                        .param("category", "Food")
                        .param("month", "bad"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateSuccess() throws Exception {
        Budget existing = Budget.builder()
                .id(5L)
                .ownerId("x")
                .category("Food")
                .monthStart(LocalDate.of(2025, 1, 1))
                .amount(new BigDecimal("100"))
                .build();

        Mockito.when(repo.findById(5L)).thenReturn(Optional.of(existing));
        Mockito.when(repo.save(any())).thenReturn(existing);

        mvc.perform(patch("/api/budgets/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        { "amount": 150 }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(150));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        Mockito.when(repo.findById(500L)).thenReturn(Optional.empty());

        mvc.perform(patch("/api/budgets/500")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 150}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteSuccess() throws Exception {
        Mockito.when(repo.existsById(30L)).thenReturn(true);

        mvc.perform(delete("/api/budgets/30"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteNotFound() throws Exception {
        Mockito.when(repo.existsById(40L)).thenReturn(false);

        mvc.perform(delete("/api/budgets/40"))
                .andExpect(status().isNotFound());
    }
}
