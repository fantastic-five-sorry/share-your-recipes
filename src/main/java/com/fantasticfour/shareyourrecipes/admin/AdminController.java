package com.fantasticfour.shareyourrecipes.admin;

import java.util.List;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.account.UserService;
import com.fantasticfour.shareyourrecipes.account.Utils;
import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.QuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.repository.QuestionRepo;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.services.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    RecipeService recipeService;
    @Autowired
    QuestionRepo questionRepo;

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
        Pageable page = PageRequest.of(0, 10);
        Page<RecipeDTO> topRecipes = recipeService.findAllSortByUpVoteCount(page);

        List<QuestionDTO> topQuestions = questionRepo.findTopQuestion().stream().map(QuestionDTO::new)
                .collect(Collectors.toList());

        model.addAttribute("admin", admin);
        model.addAttribute("topRecipes", topRecipes.getContent());

        model.addAttribute("topQuestions", topQuestions);
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
