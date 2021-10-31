package com.fantasticfour.shareyourrecipes.web;

import java.security.Principal;

import com.fantasticfour.shareyourrecipes.account.UserService;
import com.fantasticfour.shareyourrecipes.account.UserUtils;
import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.account.events.SendTokenEmailEvent;
import com.fantasticfour.shareyourrecipes.configs.UserPrincipal;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.AnswerDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.dto.QuestionDTO;
import com.fantasticfour.shareyourrecipes.questionandanswer.service.AnswerService;
import com.fantasticfour.shareyourrecipes.questionandanswer.service.QuestionService;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.services.RecipeService;
import com.fantasticfour.shareyourrecipes.votes.dtos.CommentDto;
import com.fantasticfour.shareyourrecipes.votes.dtos.CommentVoteDto;
import com.fantasticfour.shareyourrecipes.votes.services.CommentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
// @RequestMapping("/api")
public class HompageController {

    Logger logger = LoggerFactory.getLogger(HompageController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private CommentService commentService;
    @Autowired 
    private QuestionService questionService;
    @Autowired 
    private AnswerService answerService;
    

    // @
    @GetMapping(value = { "/", "/home" })
    public String getHome(Authentication authentication, Model model) {
        if (authentication != null) {
            UserUtils.getIdFromRequest(authentication).ifPresent(uid -> {
                UserInfo user = userService.getUserInfoById(uid);

                model.addAttribute("user", user);
            });
            // if (authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
            // return "redirect:/admin/hello";
            // }
        }

        return "home";
    }

    @GetMapping("/test-index")
    public String getHom2() {
        return "index";
    }

    @GetMapping("/recipe/{slug}")
    public String recipeDetailPage(@PathVariable("slug") String slug, Authentication authentication, Model model) {
        try {
            Long uid = -1L;
            RecipeDTO recipeDTO = recipeService.getRecipeBySlug(slug);
            if (authentication != null) {
                uid = UserUtils.getIdFromRequest(authentication).orElse(-1L);
            }
            Pageable page = PageRequest.of(0, 5);
            Page<CommentVoteDto> comments = commentService.getCommentVotingsOfRecipe(recipeDTO.getId(), uid, page);
            model.addAttribute("comments", comments.getContent());
            model.addAttribute("recipe", recipeDTO);
        } catch (Exception e) {
            return "error/404";
        }
        return "recipe/recipe-detail";
    }

    @GetMapping("/post-question")
    public String postQuestion() {
        return "qa/post-question";
    }

    @GetMapping("/answer/{slug}")
    public String anwerQuestion(@PathVariable("slug") String slug, Model model) {
        try {
            QuestionDTO questionDTO = questionService.getQuestionBySlug(slug);
            Pageable page = PageRequest.of(0, 2);
            Page<AnswerDTO> listAnswer = answerService.findByIdQuestion(questionDTO.getId(), page);
            model.addAttribute("listAnswer", listAnswer.getContent());
            model.addAttribute("question", questionDTO);
        } catch (Exception e) {
            //TODO: handle exception
            return "error/404";
        }
        return "qa/answer";
    }

    @GetMapping("/list-question")
    public String listQuesString(Model model) {
        Pageable page = PageRequest.of(0, 3);
        Page<QuestionDTO>  listQuestion = questionService.findAll(page);
        model.addAttribute("listQuestion", listQuestion.getContent());
        return "qa/list-question";
    }

    @GetMapping("/my-profile")
    @PreAuthorize("isAuthenticated()")
    public String myProfile(Principal principal, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "login/login";
        }
        com.fantasticfour.shareyourrecipes.configs.UserPrincipal oauthUser = (com.fantasticfour.shareyourrecipes.configs.UserPrincipal) auth
                .getPrincipal();
        Long yourId = oauthUser.getId();
        if (yourId != null) {
            model.addAttribute("userInfo", userService.getUserInfoById(yourId));
            model.addAttribute("your_email", oauthUser.getFullName());
            return "profile/my-profile";

        }
        return "error/404";
        // model.addAttribute("your_name", principal.());
    }

    @GetMapping("/international")
    public String getInternationalPage() {
        return "international";
    }

    @GetMapping("/testpage")
    public String gettestpagePagex() {
        return "testpage";
    }

    @GetMapping("/404")
    public String notfoundPage() {
        return "error/404";
    }

    @GetMapping("/test-role")
    public String testRole(Model model) {
        model.addAttribute("adminMessage", "Admin content available");
        model.addAttribute("staffMessage", "Staff content available");
        model.addAttribute("userMessage", "User content available");
        return "roleHierarchy";
    }

    @GetMapping("/profile/{uid}")
    public String userProfile(@PathVariable("uid") Long uid, Model model) {
        // System.out.println(oauthUser.getAttribute("email").toString());
        model.addAttribute("your_email", uid.toString());
        model.addAttribute("userInfo", userService.getUserInfoById(uid));
        return "profile/my-profile";
    }

    @GetMapping("/change-avatar")
    public String changeAvt(Model model) {
        // System.out.println(oauthUser.getAttribute("email").toString());
        // model.addAttribute("your_email", uid.toString());
        // model.addAttribute("userInfo", userService.getUserInfoById(uid));
        return "profile/change-avatar";
    }

    @GetMapping("/newfunc")
    public String newfunc(Model model) {
        // System.out.println(oauthUser.getAttribute("email").toString());
        // model.addAttribute("your_email", uid.toString());
        // model.addAttribute("userInfo", userService.getUserInfoById(uid));
        return "testingFunc";
    }

    @GetMapping("/users")
    public String newfuncx(Model model) {
        // System.out.println(oauthUser.getAttribute("email").toString());
        // model.addAttribute("your_email", uid.toString());
        // model.addAttribute("userInfo", userService.getUserInfoById(uid));
        return "users";
    }

    @GetMapping("/accessDenied")
    @PreAuthorize("isAuthenticated()")
    public String accessDeniedPage(Model model) {
        // System.out.println(oauthUser.getAttribute("email").toString());
        // model.addAttribute("your_email", uid.toString());
        // model.addAttribute("userInfo", userService.getUserInfoById(uid));
        return "error/403";
    }

    @GetMapping("/loginAdmin")
    public String viewLogin() {
        return "admin/login";
    }
}
