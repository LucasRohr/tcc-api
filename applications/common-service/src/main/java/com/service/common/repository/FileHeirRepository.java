package com.service.common.repository;

import com.service.common.domain.FileHeir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileHeirRepository extends JpaRepository<FileHeir, Long> {

    @Query("SELECT fh FROM FileHeir fh WHERE fh.heir.id = ?1")
    List<FileHeir> getFilesHeirsByHeir(Long heirId);

    @Query("SELECT fh FROM FileHeir fh WHERE fh.file.owner.id = ?1")
    List<FileHeir> getFilesHeirsByOwner(Long ownerId);

    @Query("SELECT fh FROM FileHeir fh WHERE fh.file.id = ?1")
    List<FileHeir> getFilesHeirsByFile(Long fileId);

}
