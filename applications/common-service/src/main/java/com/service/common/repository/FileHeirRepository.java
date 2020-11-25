package com.service.common.repository;

import com.service.common.domain.FileHeir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileHeirRepository extends JpaRepository<FileHeir, Long> {

    @Query("SELECT fh FROM FileHeir fh WHERE fh.heir.id = ?1")
    List<FileHeir> getFilesHeirsByHeir(Long heirId);
}
