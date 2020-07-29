package com.service.credential.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class DocumentController {

    @GetMapping("")
    public String test() {
        return "teste com documento ";
    }

}
