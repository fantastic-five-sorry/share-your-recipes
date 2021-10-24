package com.fantasticfour.shareyourrecipes.storages.recipe;

import java.io.IOException;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.storages.StorageFileNotFoundException;
import com.fantasticfour.shareyourrecipes.storages.StorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/storage/recipe")
@PreAuthorize("isAuthenticated()")
public class RecipeImageUploadController {

    Logger logger = LoggerFactory.getLogger(RecipeImageUploadController.class);
    private final StorageService recipeStorageService;

    @Autowired
    public RecipeImageUploadController(@Qualifier("recipe") StorageService recipeStorageService) {
        this.recipeStorageService = recipeStorageService;
    }

    @GetMapping("")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files",
                recipeStorageService.loadAll().map(path -> MvcUriComponentsBuilder
                        .fromMethodName(RecipeImageUploadController.class, "serveFile", path.getFileName().toString())
                        .build().toUri().toString()).collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = recipeStorageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("")
    public String handleFileUpload(Authentication authentication, @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        logger.info("upload file wt: " + file.getContentType());
        if (file.getContentType().contains("image")) {
            recipeStorageService.store(file);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
        } else {
            redirectAttributes.addFlashAttribute("message",
                    "Accept image type only. " + file.getOriginalFilename() + " dame dane!");
        }
        return "redirect:/storage/recipe";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
