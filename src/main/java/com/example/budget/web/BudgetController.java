package com.example.budget.web;

import com.example.budget.domain.Budget;
import com.example.budget.repository.BudgetRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetRepository repo;

    public BudgetController(BudgetRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Budget> createBudget(@Valid @RequestBody BudgetCreateRequest req) {
        LocalDate monthStart;
        try {
            monthStart = YearMonth.parse(req.month()).atDay(1);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }

        Budget b = Budget.builder()
                .ownerId(req.ownerId())
                .category(req.category())
                .monthStart(monthStart)
                .amount(req.amount())
                .build();

        Budget saved = repo.save(b);
        return ResponseEntity.created(URI.create("/api/budgets/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Budget> getById(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Budget> findByOwnerCategoryMonth(@RequestParam String ownerId,
                                                           @RequestParam String category,
                                                           @RequestParam String month) {
        LocalDate monthStart;
        try {
            monthStart = YearMonth.parse(month).atDay(1);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Budget> b = repo.findByOwnerIdAndCategoryAndMonthStart(ownerId, category, monthStart);
        return b.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Budget> update(@PathVariable Long id, @Valid @RequestBody BudgetUpdateRequest req) {
        return repo.findById(id).map(existing -> {
            existing.setAmount(req.amount());
            Budget saved = repo.save(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public record BudgetCreateRequest(@NotBlank String ownerId,
                                      @NotBlank String category,
                                      @NotBlank String month,
                                      @DecimalMin("0.00") java.math.BigDecimal amount) {
    }

    public record BudgetUpdateRequest(@DecimalMin("0.00") java.math.BigDecimal amount) {
    }
}
