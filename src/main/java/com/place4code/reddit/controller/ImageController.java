package com.place4code.reddit.controller;

import com.place4code.reddit.model.User;
import com.place4code.reddit.service.ImageService;
import com.place4code.reddit.service.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

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

        return "redirect:/user/" + user.getLogin();

    }

    @GetMapping("/user/{id}/avatar")
    public void renderAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {

        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            byte[] image = new byte[user.getImage().length];
            int i = 0;
            for (byte b :
                    user.getImage()) {
                image[i++] = b;
            }

            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(image);
            IOUtils.copy(inputStream, response.getOutputStream());

        }



    }
}
