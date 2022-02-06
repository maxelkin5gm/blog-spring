package com.blog.constrollers;

import com.blog.services.FileService;

import com.blog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class UploadController {

    FileService fileService;
    UserService userService;

    @PostMapping("/api/uploadImg")
    @PreAuthorize("isAuthenticated()")
    public String formPagePost(@RequestParam MultipartFile file, Principal principal) throws IOException {
        String[] allowedTypes = {"image/jpeg", "image/png", "image/webp"};

        var resultPath = fileService.saveUploadedFile(allowedTypes, file);

        // delete

        userService.addImg(resultPath, principal);

        return "redirect:/";
    }
}
