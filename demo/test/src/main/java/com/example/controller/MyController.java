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

    @GetMapping("/test1")
    public String test1(){
        return myService.test();
    }

    @GetMapping("/test2")
    public String test2(){
        return myService.test();
    }
}
