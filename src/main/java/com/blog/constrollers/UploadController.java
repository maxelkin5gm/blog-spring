package com.blog.constrollers;

import com.blog.services.FileService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class UploadController {

    FileService fileService;

    @PostMapping("/api/uploadImg")
    public String formPagePost(@RequestParam(required = false) MultipartFile file) throws IOException {
        String[] allowedTypes = {"image/jpeg", "image/png", "image/webp"};
        fileService.saveUploadedFile(allowedTypes, file);

        // добавление картинки челу в бд
        // тут нужно продумать над транзакцией

        return "redirect:/";
    }
}
