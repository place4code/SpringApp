package com.place4code.reddit.service;

import com.place4code.reddit.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    UserService userService;

    public ImageServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void saveImage(Long userId, MultipartFile file) {

        Optional<User> tempUser = userService.findById(userId);

        if (tempUser.isPresent()) {
            User user = tempUser.get();

            System.out.println("ID: " + user.getId());
            System.out.println("Pass: " + user.getPassword());
            System.out.println("Confirmpass: " + user.getConfirmPassword());

            try {
                Byte[] byteObject = new Byte[file.getBytes().length];
                int i = 0;
                for (byte b :
                        file.getBytes()) {
                    byteObject[i++] = b;
                }
                user.setImage(byteObject);
                System.out.println("User's ID: " + user.getId());
                System.out.println("Password: " + user.getPassword());
                user.setConfirmPassword(user.getPassword());
                System.out.println("Confirm: " + user.getConfirmPassword());
                user.setConfirmPassword(user.getPassword());
                userService.save(user);

            } catch (IOException e) {

            }

        }



        System.out.println("saveImage from ImageServiceImpl");
    }
}
