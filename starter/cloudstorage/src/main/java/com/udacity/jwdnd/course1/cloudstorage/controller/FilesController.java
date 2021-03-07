package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.files.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FilesController {
    private StatusMessageService messageService;
    private FileService fileService;

    public FilesController(StatusMessageService messageService, FileService uploadService) {
        this.messageService = messageService;
        this.fileService = uploadService;
    }

    @PostMapping("/fileUpload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model){
        fileService.copyFile(file);
        model.addAttribute("statusMessages", messageService.getStatusMessages());
        model.addAttribute("files", fileService.getFiles());
        return "home";
    }

    @GetMapping("/fileUpload")
    public String downloadFile(Model model){
        model.addAttribute("statusMessages", messageService.getStatusMessages());
        model.addAttribute("files", fileService.getFiles());
        return "home";
    }

    @GetMapping("/deleteFile/{fileName}")
    public String deleteFile(Model model, @RequestParam(name = "fileName", required = false) String fileName){
        fileService.deleteFile(fileName);
        model.addAttribute("statusMessages", messageService.getStatusMessages());
        model.addAttribute("files", fileService.getFiles());
        return "home";
    }

    @GetMapping("/viewFile/{fileName}")
    public void viewFile(Model model, HttpServletRequest request,  HttpServletResponse response, @RequestParam String fileName){

        Path file = fileService.getFilePath(fileName);

        if (Files.exists(file))
        {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            try
            {
                ServletOutputStream outputStream = response.getOutputStream();
                Files.copy(file, outputStream);
                outputStream.flush();
                outputStream.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
