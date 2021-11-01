package com.fantasticfour.shareyourrecipes.storages.avatar;

import java.io.IOException;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.account.UserService;
import com.fantasticfour.shareyourrecipes.account.Utils;
import com.fantasticfour.shareyourrecipes.storages.StorageFileNotFoundException;
import com.fantasticfour.shareyourrecipes.storages.recipe.RecipeStorageService;

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
@RequestMapping("/storage/avatar")
@PreAuthorize("isAuthenticated()")
public class AvatarUploadController {
    Logger logger = LoggerFactory.getLogger(AvatarUploadController.class);
    private final AvatarStorageService avatarStorageService;

    private final UserService userService;

    @Autowired
    public AvatarUploadController(@Qualifier("avatar") AvatarStorageService avatarStorageService,
            UserService userService) {
        this.avatarStorageService = avatarStorageService;
        this.userService = userService;
    }

    @GetMapping("")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files",
                avatarStorageService.loadAll().map(path -> MvcUriComponentsBuilder
                        .fromMethodName(AvatarUploadController.class, "serveFile", path.getFileName().toString())
                        .build().toUri().toString()).collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = avatarStorageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("")
    public String handleFileUpload(Authentication authentication, @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        logger.info("upload file wt: " + file.getContentType());
        try {
            if (file.getContentType().contains("image")) {
                String fileName = avatarStorageService.store(file);
                // update avt url to user

                String avatarUrl = "/storage/avatar/" + fileName;

                //
                Long uid = Utils.getIdFromRequest(authentication)
                        .orElseThrow(() -> new IllegalStateException("User not found"));
                       
                userService.updateAvatar(uid, avatarUrl);

                logger.info("User " + uid + " has change the avatar by the file " + avatarUrl);

                redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + fileName + "!");
            } else {
                redirectAttributes.addFlashAttribute("message",
                        "Accept image type only. " + file.getOriginalFilename() + " dame dane!");
            }
            return "redirect:/storage/avatar";
        } catch (Exception e) {
            return "404";
        }

    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}