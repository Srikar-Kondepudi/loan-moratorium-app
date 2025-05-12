package com.loanmoratorium.repository;

import com.loanmoratorium.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByAccountNumber(String accountNumber);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 