package com.example.java1234.controller;

import com.example.java1234.entity.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/java1234")
public class TestController {
    @GetMapping("/test")
    public R test(){
        return R.ok("java1234");
    }
}
