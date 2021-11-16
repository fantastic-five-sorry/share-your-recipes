package com.fantasticfour.shareyourrecipes.recipes.controllers;

import javax.servlet.http.HttpServletRequest;

import com.fantasticfour.shareyourrecipes.storages.recipe.RecipeStorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/chef")
@PreAuthorize("hasRole('CHEF')")
public class RecipePageController {

    Logger logger = LoggerFactory.getLogger(RecipePageController.class);
    @Autowired
    RecipeStorageService recipeStorageService;

    // @PostMapping("/createRecipe")
    // @ResponseBody
    // public ResponseEntity<?> handleChangeAvatar(Authentication authentication,
    // @RequestParam("image") MultipartFile file, RedirectAttributes
    // redirectAttributes,
    // HttpServletRequest request) {
    // try {
    // if (file.getContentType().contains("image")) {
    // logger.info("upload file wt: " + file.getContentType());
    // logger.info(request.getAttribute("steps").toString());
    // logger.info(request.getAttribute("title").toString());
    // logger.info(request.getAttribute("guideUrl").toString());
    // logger.info(request.getAttribute("ingredients").toString());
    // String fileName = recipeStorageService.store(file);
    // // update avt url to user
    // String recipesImg = "/storage/recipe/" + fileName;
    // //
    // // Long uid = Utils.getIdFromRequest(authentication)
    // // .orElseThrow(() -> new IllegalStateException("User not found"));
    // // userService.updateAvatar(uid, avatarUrl);
    // logger.info("User" + " has change the avatar by the file " + recipesImg);
    // redirectAttributes.addFlashAttribute("message", "You successfully uploaded "
    // + fileName + "!");
    // } else {
    // redirectAttributes.addFlashAttribute("message",
    // "Accept image type only. " + file.getOriginalFilename() + " dame dane!");
    // }
    // return ResponseEntity.ok().body("Success");
    // } catch (Exception e) {
    // return ResponseEntity.badRequest().body(e.getMessage());
    // }
    // }

    @GetMapping("/")

    private String home() {
        return "recipe/create";
    }

    
}
