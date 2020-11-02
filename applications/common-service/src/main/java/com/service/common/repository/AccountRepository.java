package com.service.common.repository;

import com.service.common.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT ac FROM Account ac WHERE ac.user.id = ?1 ORDER BY ac.updatedAt DESC")
    List<Account> getAllUserAccounts(Long id);

}

