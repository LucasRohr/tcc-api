package com.service.video.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("")
    public String test() {
        return "teste com video ";
    }

    @GetMapping("image-test")
    public String testImage() {
        return restTemplate.getForObject("http://image-service/images", String.class);
    }

}
