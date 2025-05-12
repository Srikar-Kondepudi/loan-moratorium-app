package com.loanmoratorium.controller;

import com.loanmoratorium.model.Loan;
import com.loanmoratorium.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@CrossOrigin(origins = "http://localhost:3000")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody Loan loan, @RequestParam Long userId) {
        try {
            Loan createdLoan = loanService.createLoan(loan, userId);
            return ResponseEntity.ok(createdLoan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserLoans(@PathVariable Long userId) {
        try {
            List<Loan> loans = loanService.getUserLoans(userId);
            return ResponseEntity.ok(loans);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{loanId}/approve")
    public ResponseEntity<?> approveLoan(@PathVariable Long loanId) {
        try {
            Loan loan = loanService.approveLoan(loanId);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{loanId}/moratorium")
    public ResponseEntity<?> requestMoratorium(@PathVariable Long loanId) {
        try {
            Loan loan = loanService.requestMoratorium(loanId);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{loanId}/cancel-moratorium")
    public ResponseEntity<?> cancelMoratorium(@PathVariable Long loanId) {
        try {
            Loan loan = loanService.cancelMoratorium(loanId);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/moratorium/active")
    public ResponseEntity<?> getActiveMoratoriumLoans() {
        try {
            List<Loan> loans = loanService.getActiveMoratoriumLoans();
            return ResponseEntity.ok(loans);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 