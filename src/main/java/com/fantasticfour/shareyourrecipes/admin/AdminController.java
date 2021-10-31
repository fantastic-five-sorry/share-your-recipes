package com.fantasticfour.shareyourrecipes.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @GetMapping("hello")
    @ResponseBody
    public String sayHi(@RequestParam(name = "name", required = false) String name) {

        return name == null ? "lvl" : name;
    }

    @GetMapping("")
    @ResponseBody
    public String hompage(@RequestParam(name = "name", required = false) String name) {

        return name == null ? "hello admin home page" : name;
    }

    
}
