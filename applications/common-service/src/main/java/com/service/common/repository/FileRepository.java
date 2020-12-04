package com.service.common.repository;


import com.service.common.domain.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends JpaRepository<File, Long> {

    @Query("SELECT f from File f WHERE f.owner.id = :ownerId AND f.type = :type AND f.isActive = true")
    Page<File> getOwnerFilesByType(
            Pageable pageable,
            @Param("ownerId") Long ownerId,
            @Param("type") String type
    );

}
