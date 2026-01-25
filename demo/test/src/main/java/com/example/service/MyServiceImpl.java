package com.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyServiceImpl implements MyService {

    @Override
    public String test() {
        return "OK";
    }

}
