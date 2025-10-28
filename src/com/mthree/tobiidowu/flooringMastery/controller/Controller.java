package com.mthree.tobiidowu.flooringMastery.controller;

import com.mthree.tobiidowu.flooringMastery.service.ServiceLayer;
import com.mthree.tobiidowu.flooringMastery.view.View;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Component
public class Controller {
    ServiceLayer service;
    View view;

    @Autowired
    public Controller(ServiceLayer service, View view) {
        this.service = service;
        this.view = view;
    }
}
