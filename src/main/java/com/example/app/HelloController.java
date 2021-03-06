package com.example.app;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

//    @GetMapping("/hello")
//    public String handle(Model model) {
//        model.addAttribute("message", "Hello World!");
//        return "index";
//    }

    @GetMapping("/hello")
    public HelloResponse handle(Model model) {
        return new HelloResponse("hoge", 18);
    }

    @PostMapping("/post")
    public HelloResponse post(@RequestBody HelloRequest request) {
        return new HelloResponse(request.getName(), request.getAge());
    }
}
