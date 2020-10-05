package com.springbootapi.demo.Api;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class LogTestController {

    @GetMapping("/log")
    public String log() {
        log.info("info -- log");
        log.warn("warn -- log");
        log.error("error -- log");
        String name = "psq";
        String email = "163@163.com";
        log.info("name: {}, email: {}", name, email);
        return "log test";
    }
}
