package me.colombiano.kurs.maven3.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {

    @RequestMapping("/helloWorld")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello world from controller!");
        return "helloWorld";
    }

    @RequestMapping("/test")
    public String getTestMessage(Model model) {
        Test test = new Test("Test message");

        model.addAttribute("test", test);

        return "test";
    }
}
