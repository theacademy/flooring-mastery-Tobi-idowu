package com.mthree.tobiidowu.flooringMastery;

import com.mthree.tobiidowu.flooringMastery.controller.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        // Bootstraps Spring — creates and wires all @Component / @Service / @Repository
        // beans
        ApplicationContext context = SpringApplication.run(App.class, args);

        // Retrieve your Controller bean from the context
        Controller controller = context.getBean(Controller.class);

        // Start the program
        controller.run();
    }
}