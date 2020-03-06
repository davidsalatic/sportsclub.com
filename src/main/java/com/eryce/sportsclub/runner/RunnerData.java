package com.eryce.sportsclub.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class RunnerData implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println("***************APP STARTED*****************");
    }
}