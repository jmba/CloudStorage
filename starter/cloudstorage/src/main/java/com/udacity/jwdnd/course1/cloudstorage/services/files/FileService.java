package com.udacity.jwdnd.course1.cloudstorage.services.files;

import com.udacity.jwdnd.course1.cloudstorage.model.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.pojos.File;
import com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Service
public class FileService {
    private HashMap<String, File> files;
    private StatusMessageService statusMessageService;
    private FileMapper fileMapper;

    public FileService(StatusMessageService statusMessageService, FileMapper fileMapper) {
        this.files = new HashMap<>();
        this.statusMessageService = statusMessageService;
        this.fileMapper = fileMapper;
    }

    public List<File> getFiles(Integer userId) {
        return fileMapper.getAllFiles(userId);
    }

    public File getFile(String fileName, Integer userID) {
        return fileMapper.getFileByName(fileName, userID);
    }

    public void deleteFile(String fileName, Integer userId) {
        fileMapper.deleteFile(fileName, userId);
    }

    public void addFile(MultipartFile file, Integer userId) {
        try {
            File filePojo = new File();
            filePojo.setFiledata(file.getBytes());
            filePojo.setFilename(file.getOriginalFilename());
            filePojo.setFilesize(file.getSize());
            filePojo.setContenttype(file.getContentType());
            filePojo.setUserid(userId);

            fileMapper.insertFile(filePojo);

            this.statusMessageService.addMessage("File: " + file.getOriginalFilename() + " successfully uploaded.");
        } catch (Exception e) {
            this.statusMessageService.addMessage("Error while uploading File: " + file.getOriginalFilename());
            e.printStackTrace();
        }
    }
}

