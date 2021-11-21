package com.fantasticfour.shareyourrecipes.admin;

import com.fantasticfour.shareyourrecipes.account.UserService;
import com.fantasticfour.shareyourrecipes.account.Utils;
import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/recipe")
    public String showRecipeManagementPage(Authentication auth, Model model) {
        Long adminId = Utils.getIdFromRequest(auth).orElse(null);
        if (adminId == null)
            return "404";
        UserInfo admin = userService.getUserInfoById(adminId);
        model.addAttribute("admin", admin);
        return "admin/recipe-manage";
    }

    @GetMapping("/report")
    public String showReportManagementPage(Authentication auth, Model model) {
        Long adminId = Utils.getIdFromRequest(auth).orElse(null);
        if (adminId == null)
            return "404";
        UserInfo admin = userService.getUserInfoById(adminId);
        model.addAttribute("admin", admin);
        return "admin/report-manage";
    }

    @GetMapping("/question")
    public String showQuestionManagementPage(Authentication auth, Model model) {
        Long adminId = Utils.getIdFromRequest(auth).orElse(null);
        if (adminId == null)
            return "404";
        UserInfo admin = userService.getUserInfoById(adminId);
        model.addAttribute("admin", admin);
        return "admin/question-manage";
    }

    @GetMapping("/user")
    public String showUserManagementPage(Authentication auth, Model model) {
        Long adminId = Utils.getIdFromRequest(auth).orElse(null);
        if (adminId == null)
            return "404";
        UserInfo admin = userService.getUserInfoById(adminId);
        model.addAttribute("admin", admin);
        return "admin/user-manage";
    }

    @GetMapping("/stats")
    public String showStatsPage(Authentication auth, Model model) {
        Long adminId = Utils.getIdFromRequest(auth).orElse(null);
        if (adminId == null)
            return "404";
        UserInfo admin = userService.getUserInfoById(adminId);
        model.addAttribute("admin", admin);
        return "admin/stats";
    }

    @GetMapping("/addChef")
    public String showAddChefPage(Authentication auth, Model model) {
        Long adminId = Utils.getIdFromRequest(auth).orElse(null);
        if (adminId == null)
            return "404";
        UserInfo admin = userService.getUserInfoById(adminId);
        model.addAttribute("admin", admin);
        return "admin/add-chef";
    }

    @GetMapping("/report/detail")
    public String showReportDetailPage(Authentication auth, Model model) {
        Long adminId = Utils.getIdFromRequest(auth).orElse(null);
        if (adminId == null)
            return "404";
        UserInfo admin = userService.getUserInfoById(adminId);
        model.addAttribute("admin", admin);
        return "admin/report-detail";
    }

}
