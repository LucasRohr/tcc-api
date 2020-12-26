package com.service.user.clients;

import com.service.common.dto.FileHeirDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "file", url = "http://localhost:8200")
public interface FileClient {
    @RequestMapping(method = RequestMethod.GET, value = "/files/heir-file/all?heir_id={heirId}")
    List<FileHeirDto> getAllFilesByHeir(@PathVariable("heirId") Long heirId);

    @RequestMapping(method = RequestMethod.PUT, value = "/files/file-heir-unlink?heir_id={heirId}")
    void unlinkFileHeirs(@PathVariable("heirId") Long heirId, @RequestBody List<Long> fileHeirIds);
}
