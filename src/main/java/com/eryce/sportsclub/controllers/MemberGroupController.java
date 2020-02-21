package com.eryce.sportsclub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberGroupController {


    @GetMapping("/giveStuffPlease")
    public String give() {
        return "Radi";
    }
}
