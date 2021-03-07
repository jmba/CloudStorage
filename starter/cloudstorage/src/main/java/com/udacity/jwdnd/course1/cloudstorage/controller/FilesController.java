package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.pojos.FileUploadForm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/fileUpload")
public class FilesController {
    private static final String UPLOAD_DIR = "FileUpload/";

    public FilesController() {
        Boolean uploadDirCreated = new File(UPLOAD_DIR).mkdirs();
    }

    @GetMapping
    public String uploadFile(Model model){
        return "home";
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model){

        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
        }

        try {
            Path path = Paths.get(UPLOAD_DIR + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "home";
    }
}
