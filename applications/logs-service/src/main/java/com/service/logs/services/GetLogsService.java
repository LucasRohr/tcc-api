package com.service.logs.services;

import com.service.logs.domain.log.Log;
import com.service.logs.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class GetLogsService {

    @Autowired
    private LogsRepository logsRepository;

    private LocalDateTime convertDateToLocalDateTime(Date date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()) ;
        long minutesIntoTheDay = ChronoUnit.MINUTES.between(
                zonedDateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()), zonedDateTime
        );

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public Page<Log> getLogs(
            Pageable pageable,
            String filterText,
            Date fromDate,
            Date toDate,
            String order) {

        Sort.Direction pageOrder = Sort.Direction.DESC;

        if(order != null) {
            pageOrder = order.equals(Sort.Direction.ASC.toString()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        }

        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(), 10,
                Sort.by(pageOrder, "createdAt"));

        LocalDateTime initialDate = fromDate != null ? convertDateToLocalDateTime(fromDate) : null;
        LocalDateTime endDate = toDate != null ? convertDateToLocalDateTime(toDate) : null;

        if(initialDate != null && endDate != null) {
            return logsRepository.getLogsPageBetweenPeriod(
                    pageRequest,
                    filterText,
                    initialDate,
                    endDate
            );
        }

        if(initialDate != null) {
            return logsRepository.getLogsPageGreaterThanDate(
                    pageRequest,
                    filterText,
                    initialDate
            );
        }

        if(endDate != null) {
            return logsRepository.getLogsPageLesserThanDate(
                    pageRequest,
                    filterText,
                    endDate
            );
        }

        return logsRepository.getLogsPageWithoutDate(
                pageRequest,
                filterText
        );
    }

}
