package ru.job4j.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexControl {
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
