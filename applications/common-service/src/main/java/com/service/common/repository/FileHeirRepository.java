package com.service.common.repository;

import com.service.common.domain.File;
import com.service.common.domain.FileHeir;
import com.service.common.enums.FileTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileHeirRepository extends JpaRepository<FileHeir, Long> {

    @Query("SELECT fh FROM FileHeir fh WHERE fh.file.owner.id = ?1")
    List<FileHeir> getFilesHeirsByOwner(Long ownerId);

    @Query("SELECT fh FROM FileHeir fh WHERE fh.file.id = ?1")
    List<FileHeir> getFilesHeirsByFile(Long fileId);

    @Query("SELECT fh.file from FileHeir fh " +
           "WHERE fh.heir.id = :heirId AND fh.file.type = :type AND fh.file.isActive = true")
    Page<File> getHeirsFilesByType(
            Pageable pageable,
            @Param("heirId") Long heirId,
            @Param("type") FileTypeEnum type
    );

    @Query("SELECT fh from FileHeir fh" +
            "WHERE fh.heir.id = :heirId AND fh.file.isActive = true")
    List<FileHeir> getHeirsFilesByHeir(@Param("heirId") Long heirId);

}
