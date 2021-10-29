package com.fantasticfour.shareyourrecipes.chef;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chef")
@PreAuthorize("hasRole('CHEF')")
public class ChefController {
    @GetMapping("hello")
    public String sayHi(@RequestParam(name = "name", required = false) String name) {

        return name == null ? "lvl" : name;
    }
}
