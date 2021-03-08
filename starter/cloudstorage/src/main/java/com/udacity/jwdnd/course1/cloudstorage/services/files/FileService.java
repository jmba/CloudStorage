package com.udacity.jwdnd.course1.cloudstorage.services.files;

import com.udacity.jwdnd.course1.cloudstorage.model.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.pojos.File;
import com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class FileService {
    private StatusMessageService statusMessageService;
    private FileMapper fileMapper;

    public FileService(StatusMessageService statusMessageService, FileMapper fileMapper) {
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

    public void addFile(MultipartFile file, Integer userId) throws IOException {
            File filePojo = getFileObject(file, userId);
            fileMapper.insertFile(filePojo);
    }

    private File getFileObject(MultipartFile multipartFile, Integer userId) throws IOException {
        File filePojo = new File();
        filePojo.setFiledata(multipartFile.getBytes());
        filePojo.setFilename(multipartFile.getOriginalFilename());
        filePojo.setFilesize(multipartFile.getSize());
        filePojo.setContenttype(multipartFile.getContentType());
        filePojo.setUserid(userId);
        return filePojo;
    }
}

