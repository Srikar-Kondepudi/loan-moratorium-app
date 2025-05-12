package com.loanmoratorium.repository;

import com.loanmoratorium.model.Loan;
import com.loanmoratorium.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId);
    List<Loan> findByStatus(LoanStatus status);
    
    @Query("SELECT l FROM Loan l WHERE l.isMoratoriumActive = true")
    List<Loan> findActiveMoratoriumLoans();
    
    @Query("SELECT l FROM Loan l WHERE l.user.id = ?1 AND l.status = ?2")
    List<Loan> findByUserIdAndStatus(Long userId, LoanStatus status);
} 