package com.service.common.repository;

import com.service.common.domain.Heir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HeirRepository extends JpaRepository<Heir, Long> {

    @Query("SELECT h FROM Heir h " +
            "WHERE h.owner.id = ?1 AND h.status = 'ACTIVE'")
    List<Heir> getOwnerHeirs(Long ownerId);

}
