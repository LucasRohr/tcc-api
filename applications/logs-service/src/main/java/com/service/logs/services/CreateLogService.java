package com.service.logs.services;

import com.service.logs.controller.requests.CreateLogRequest;
import com.service.logs.domain.log.Log;
import com.service.logs.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class CreateLogService {

    @Autowired
    private LogsRepository logsRepository;

    public void createLog(CreateLogRequest createLogRequest) {
        String content = createLogRequest.getContent();
        LocalDateTime createdAt = LocalDateTime.now();

        Log log = new Log(content, createdAt);

        logsRepository.save(log);
    }

}
