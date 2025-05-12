package com.loanmoratorium.model;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "interest_rate", nullable = false)
    private BigDecimal interestRate;

    @Column(name = "loan_term_months", nullable = false)
    private Integer loanTermMonths;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    @Column(name = "remaining_balance")
    private BigDecimal remainingBalance;

    @Column(name = "moratorium_start_date")
    private LocalDateTime moratoriumStartDate;

    @Column(name = "moratorium_end_date")
    private LocalDateTime moratoriumEndDate;

    @Column(name = "is_moratorium_active")
    private boolean isMoratoriumActive = false;
} 