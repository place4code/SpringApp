package com.place4code.reddit.controller;

import com.place4code.reddit.model.User;
import com.place4code.reddit.service.ImageService;
import com.place4code.reddit.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ImageController {

    private UserService userService;
    private ImageService imageService;

    public ImageController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @Secured({"ROLE_USER"})
    @PostMapping("/edit/photo")
    public String handleFileUpload(@RequestParam("imagefile") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        imageService.saveImage(user.getId(), file);
/*
        if (!file.isEmpty()) {
            if (file.getContentType().equalsIgnoreCase("image/jpeg")) {

                storageService.store(file);

                Optional<User> tempUser = userService.findByEmail(user.getEmail());
                if (tempUser.isPresent()) {
                    User userToSave = tempUser.get();
                    userToSave.setAvatar(true);
                    userToSave.setConfirmPassword(user.getPassword());
                    userService.save(userToSave);
                }


                redirectAttributes.addFlashAttribute("message",
                        "The photo has been changed");
                redirectAttributes.addFlashAttribute("error",
                        false);



                return "redirect:/user/" + user.getLogin();

            } else {

                redirectAttributes.addFlashAttribute("message",
                        "The file must be a picture");
                redirectAttributes.addFlashAttribute("error",
                        true);

            }
        }*/

        return "redirect:/user/" + user.getLogin();

    }
}
