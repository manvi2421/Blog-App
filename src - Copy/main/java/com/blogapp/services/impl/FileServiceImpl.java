package com.blogapp.services.impl;

import com.blogapp.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //  File Name
        String name = file.getOriginalFilename();

        //  Generate Random name for File
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        //  Full Path
        String filePath = path + File.separator + fileName;

        //  Create image folder if not created
        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdir();
        }

        //  File Upload
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        InputStream inputStream = new FileInputStream(filePath);
        return inputStream;
    }
}
