package com.service.common.repository;

import com.service.common.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("SELECT o FROM Owner o WHERE o.account.id = ?1")
    Owner findByAccountId(Long accountId);

}
