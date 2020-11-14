package com.example.study.controller;

import com.example.study.model.network.Header;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class GetController {

    @GetMapping("/header")
    public Header getHeader(){

        return Header.builder().resultCode("OK").description("OK").build();
    }


}
