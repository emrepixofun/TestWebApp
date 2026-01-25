package com.example.controller;

import lombok.AllArgsConstructor;
import com.example.service.MyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MyController {

    private final MyService myService;

    @GetMapping("/test")
    public String test(){
        return myService.test();
    }
}
