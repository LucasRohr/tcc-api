package com.service.logs.repository;

import com.service.logs.domain.log.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface LogsRepository extends JpaRepository<Log, Long> {

    @Query("SELECT log FROM Log log WHERE log.content LIKE %:filter%")
    Page<Log> getLogsPageWithoutDate(
            Pageable pageable,
            @Param("filter") String filterText);

    @Query("SELECT log FROM Log log WHERE log.content LIKE %:filter% " +
            "AND log.createdAt BETWEEN :fromDate AND :toDate ")
    Page<Log> getLogsPageBetweenPeriod(
            Pageable pageable,
            @Param("filter") String filterText,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    @Query("SELECT log FROM Log log WHERE log.content LIKE %:filter% " +
            "AND log.createdAt >= :fromDate ")
    Page<Log> getLogsPageGreaterThanDate(
            Pageable pageable,
            @Param("filter") String filterText,
            @Param("fromDate") LocalDateTime fromDate
    );

    @Query("SELECT log FROM Log log WHERE log.content LIKE %:filter% " +
            "AND log.createdAt <= :toDate ")
    Page<Log> getLogsPageLesserThanDate(
            Pageable pageable,
            @Param("filter") String filterText,
            @Param("toDate") LocalDateTime toDate
    );


}
