package com.blog.services;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public interface FileService {

    void saveUploadedFile(String[] allowedTypes, MultipartFile file) throws IOException, ResponseStatusException;
}
