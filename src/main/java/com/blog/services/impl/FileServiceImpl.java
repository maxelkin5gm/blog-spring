package com.blog.services.impl;


import com.blog.config.MvcConfig;

import com.blog.services.FileService;
import com.blog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final String uploadPath = MvcConfig.uploadPath;

    // check and/or create uploadDir
    public void checkUploadDir() {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            var status = uploadDir.mkdir();
            if (!status) throw new SecurityException();
        }
    }

    public boolean isValidImg(String[] allowedTypes, MultipartFile file) {
        var fileType = file.getContentType();
        return Arrays.asList(allowedTypes).contains(fileType);
    }

    @Override
    public String saveUploadedFile(String[] allowedTypes, MultipartFile file) throws IOException, ResponseStatusException {
        checkUploadDir();

        if (file == null || file.isEmpty() || !isValidImg(allowedTypes, file) || file.getOriginalFilename() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        var resultFilename = UUID.randomUUID() + "." + file.getOriginalFilename().split("\\.")[1];
        file.transferTo(new File(MvcConfig.uploadPath + resultFilename));

        return "/uploads/" + resultFilename;
    }

}
