package com.place4code.reddit.storage;

import com.place4code.reddit.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private boolean fileAgreed = false;

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


        if ((file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/jpg"))
        && file.getSize() < 1048576) {
            fileAgreed = true;
        } else {
            throw new RuntimeException("FAIL!");
        }


        System.out.println(file.getContentType());

        if (fileAgreed) {
            // Who is logged
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            delete(user.getLogin() + ".jpg");

            try {
                Files.copy(file.getInputStream(), this.rootLocation.resolve(user.getLogin() + ".jpg"));
                user.setAvatar(true);
            } catch (Exception e) {
                throw new RuntimeException("FAIL! -> message = " + e.getMessage());
            }
        }

    }

}