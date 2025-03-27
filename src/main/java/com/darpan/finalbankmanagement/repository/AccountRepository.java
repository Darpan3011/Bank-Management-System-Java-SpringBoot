package com.darpan.finalbankmanagement.repository;

import com.darpan.finalbankmanagement.entity.UserAccount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_accounts u SET u.bank_balance = :balance WHERE u.id = :userId", nativeQuery = true)
    void updateBalanceByUserId(Long userId, Double balance);
}
