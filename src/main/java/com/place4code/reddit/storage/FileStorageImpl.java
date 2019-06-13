package com.place4code.reddit.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageImpl implements FileStorage{

    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final Path rootLocation = Paths.get("filestorage");

    @Override
    public void delete(String fileName) {
        try {
            FileSystemUtils.deleteRecursively(Paths.get("filestorage/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void store(MultipartFile file){


        delete("test.png");

        try {

            Files.copy(file.getInputStream(), this.rootLocation.resolve("test.png"));
        } catch (Exception e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }

}