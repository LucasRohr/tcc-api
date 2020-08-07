package com.service.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/gateway")
@RestController
public class Test {

    @GetMapping("/teste")
    public String method() {
        return "test";
    }

}
