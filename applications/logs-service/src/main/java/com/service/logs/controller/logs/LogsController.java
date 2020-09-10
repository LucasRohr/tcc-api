package com.service.logs.controller.logs;

import com.service.logs.controller.requests.CreateLogRequest;
import com.service.logs.domain.log.Log;
import com.service.logs.services.CreateLogService;
import com.service.logs.services.GetLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController()
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    private CreateLogService createLogService;

    @Autowired
    private GetLogsService getLogsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("log-creation")
    public void createLog(@RequestBody @Valid CreateLogRequest createLogRequest) {
        createLogService.createLog(createLogRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("logs-list")
    public Page<Log> getLogsList(
            Pageable pageable,
            @RequestParam(value = "filter_text", required = false) String filterText,
            @RequestParam(value = "from_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam(value = "to_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate,
            @RequestParam(value = "order", required = false) String order
            ) {

        return getLogsService.getLogs(pageable, filterText, fromDate, toDate, order);
    }

}
