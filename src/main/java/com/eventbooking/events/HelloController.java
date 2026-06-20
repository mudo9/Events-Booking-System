package com.eventbooking.events;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from my spring endpoint.";
    }

    @GetMapping("/name")
    public String myName() {
        return "My name is Michael.";
    }
}
