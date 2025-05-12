package com.loanmoratorium.service;

import com.loanmoratorium.model.Loan;
import com.loanmoratorium.model.LoanStatus;
import com.loanmoratorium.model.User;
import com.loanmoratorium.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserService userService;

    public Loan createLoan(Loan loan, Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        loan.setUser(user);
        loan.setStatus(LoanStatus.PENDING);
        loan.setStartDate(LocalDateTime.now());
        loan.setEndDate(calculateEndDate(loan.getStartDate(), loan.getLoanTermMonths()));
        loan.setMonthlyPayment(calculateMonthlyPayment(loan));
        loan.setRemainingBalance(loan.getAmount());
        
        return loanRepository.save(loan);
    }

    public Loan approveLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        
        loan.setStatus(LoanStatus.ACTIVE);
        return loanRepository.save(loan);
    }

    public Loan requestMoratorium(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStatus() != LoanStatus.ACTIVE) {
            throw new RuntimeException("Only active loans can be put under moratorium");
        }

        loan.setStatus(LoanStatus.MORATORIUM);
        loan.setMoratoriumStartDate(LocalDateTime.now());
        loan.setMoratoriumEndDate(LocalDateTime.now().plusMonths(3)); // 3 months moratorium
        loan.setMoratoriumActive(true);

        return loanRepository.save(loan);
    }

    public Loan cancelMoratorium(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!loan.isMoratoriumActive()) {
            throw new RuntimeException("Loan is not under moratorium");
        }

        loan.setStatus(LoanStatus.ACTIVE);
        loan.setMoratoriumActive(false);
        loan.setMoratoriumEndDate(LocalDateTime.now());

        return loanRepository.save(loan);
    }

    public List<Loan> getUserLoans(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    public List<Loan> getActiveMoratoriumLoans() {
        return loanRepository.findActiveMoratoriumLoans();
    }

    private LocalDateTime calculateEndDate(LocalDateTime startDate, Integer termMonths) {
        return startDate.plusMonths(termMonths);
    }

    private BigDecimal calculateMonthlyPayment(Loan loan) {
        // Using the formula: P = (P0 * r * (1 + r)^n) / ((1 + r)^n - 1)
        // where P0 is the loan amount, r is the monthly interest rate, and n is the number of months
        BigDecimal monthlyRate = loan.getInterestRate().divide(new BigDecimal("100"), 8, RoundingMode.HALF_UP)
                .divide(new BigDecimal("12"), 8, RoundingMode.HALF_UP);
        
        BigDecimal numerator = loan.getAmount().multiply(monthlyRate)
                .multiply(BigDecimal.ONE.add(monthlyRate).pow(loan.getLoanTermMonths()));
        
        BigDecimal denominator = BigDecimal.ONE.add(monthlyRate).pow(loan.getLoanTermMonths()).subtract(BigDecimal.ONE);
        
        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }
} 