package com.udacity.jwdnd.course1.cloudstorage.services.files;

import com.udacity.jwdnd.course1.cloudstorage.model.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.pojos.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.services.shared.StatusMessageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private StatusMessageService statusMessageService;
    private FileMapper fileMapper;

    public FileService(StatusMessageService statusMessageService, FileMapper fileMapper) {
        this.statusMessageService = statusMessageService;
        this.fileMapper = fileMapper;
    }

    public List<FileForm> getFiles(Integer userId) {
        return fileMapper.getAllFiles(userId);
    }

    public FileForm getFile(String fileName, Integer userID) {
        return fileMapper.getFileByName(fileName, userID);
    }

    public void deleteFile(String fileName, Integer userId) {
        fileMapper.deleteFile(fileName, userId);
    }

    public void addFile(MultipartFile file, Integer userId) throws IOException {
            FileForm fileFormPojo = getFileObject(file, userId);
            fileMapper.insertFile(fileFormPojo);
    }

    private FileForm getFileObject(MultipartFile multipartFile, Integer userId) throws IOException {
        FileForm fileFormPojo = new FileForm();
        fileFormPojo.setFiledata(multipartFile.getBytes());
        fileFormPojo.setFilename(multipartFile.getOriginalFilename());
        fileFormPojo.setFilesize(multipartFile.getSize());
        fileFormPojo.setContenttype(multipartFile.getContentType());
        fileFormPojo.setUserid(userId);
        return fileFormPojo;
    }
}

