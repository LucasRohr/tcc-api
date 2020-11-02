package com.service.common.repository;

import com.service.common.domain.Heir;
import com.service.common.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.List;

public interface HeirRepository extends JpaRepository<Heir, Long> {

    @Query("SELECT h FROM Heir h " +
            "WHERE h.owner.id = ?1 AND h.status = 'ACCEPTED'")
    List<Heir> getOwnerHeirs(Long ownerId);

    @Query("SELECT h FROM Heir h WHERE h.owner.id = ?1")
    List<Heir> getAllOwnerHeirs(Long ownerId);
}
