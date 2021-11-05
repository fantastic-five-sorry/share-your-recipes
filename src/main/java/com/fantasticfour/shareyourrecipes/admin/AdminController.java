package com.fantasticfour.shareyourrecipes.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
// @PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("/recipe")
    public String showRecipeManagementPage() {
        return "admin/recipe-manage";
    }

    @GetMapping("/report")
    public String showReportManagementPage() {
        return "admin/report-manage";
    }

    @GetMapping("/question")
    public String showQuestionManagementPage() {
        return "admin/question-manage";
    }

    @GetMapping("/user")
    public String showUserManagementPage() {
        return "admin/user-manage";
    }
    @GetMapping("/stats")
    public String showStatsPage() {
        return "admin/stats";
    }

    @GetMapping("/report/detail")
    public String showReportDetailPage() {
        return "admin/report-detail";
    }

}
