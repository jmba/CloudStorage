package com.udacity.jwdnd.course1.cloudstorage.services.files;

import com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {
    private List<String> files;
    private final String STORAGE_PATH;
    private StatusMessageService statusMessageService;

    public FileService(StatusMessageService statusMessageService) {
        this.STORAGE_PATH = getStoragePath();
        this.files = loadFilesFromHdd();
        this.statusMessageService = statusMessageService;
    }

    public String getStoragePath() {
        String uploadDirectory = "FileUpload/";
        Boolean uploadDirCreated = new File(uploadDirectory).mkdirs();
        return uploadDirectory;
    }

    public Path getFilePath(String fileName) {
        return Paths.get(STORAGE_PATH, fileName);
    }

    public List<String> getFiles() {
        return files;
    }

    public void deleteFile(String fileName) {
        if (!files.contains(fileName) || StringUtils.isEmpty(fileName)) {
            this.statusMessageService.addMessage("File: " + fileName + "does not exist.");
            return;
        }

        Path path = Paths.get(STORAGE_PATH + StringUtils.cleanPath(fileName));
        File file = path.toFile();

        try {
            file.delete();
            boolean isRemoved = files.remove(fileName);
            this.statusMessageService.addMessage("File: " + fileName + " successfully deleted.");
        } catch (Exception e) {
            this.statusMessageService.addMessage("Error while deleting File: " + fileName);
            e.printStackTrace();
        }

    }

    public void copyFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            if (files.contains(fileName)) {
                this.statusMessageService.addMessage("File: " + file.getOriginalFilename() + " already exists (upload canceled).");
                return;
            }

            Path path = Paths.get(STORAGE_PATH + StringUtils.cleanPath(fileName));
            Files.copy(file.getInputStream(), path);
            files.add(fileName);
            this.statusMessageService.addMessage("File: " + file.getOriginalFilename() + " successfully uploaded.");

        } catch (IOException e) {
            this.statusMessageService.addMessage("Error while uploading File: " + file.getOriginalFilename());
            e.printStackTrace();
        }
    }

    private List<String> loadFilesFromHdd() {
        ArrayList<String> fileList;
        try {
            File f = new File(STORAGE_PATH);
            fileList = new ArrayList<>(Arrays.asList(f.list()));
        } catch (Exception e) {
            this.statusMessageService.addMessage("Error while loading existing files from cloud drive.");
            fileList = new ArrayList<String>();
            e.printStackTrace();
        }

        return fileList;
    }
}

