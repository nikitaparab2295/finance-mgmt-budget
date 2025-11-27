package com.example.budget.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "budgets", uniqueConstraints = @UniqueConstraint(columnNames =
        {"owner_id","category","month_start"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private String category;

    @Column(name = "month_start", nullable = false)
    private LocalDate monthStart;

    @Column(nullable = false)
    private BigDecimal amount;
}
