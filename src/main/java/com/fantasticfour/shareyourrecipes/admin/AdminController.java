package com.fantasticfour.shareyourrecipes.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("hello")
    public String sayHi(@RequestParam(name="name", required = false) String name) {

        return name == null ? "lvl" : name;
    }
}
