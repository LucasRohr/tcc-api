package com.service.common.repository;

import com.service.common.domain.Account;
import com.service.common.domain.User;
import com.service.common.enums.AccountTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT ac FROM Account ac WHERE ac.user.id = ?1")
    List<Account> getAllUserAccounts(Long id);

}
