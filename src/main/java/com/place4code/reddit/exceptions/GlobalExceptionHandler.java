package com.place4code.reddit.exceptions;

import com.place4code.reddit.model.User;
import com.place4code.reddit.storage.StorageException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public String tooBig(MultipartException e, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message",
                "The file must be less than 1MB");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "redirect:/user/" + user.getLogin();

    }

    @ExceptionHandler(StorageException.class)
    public String storageException(StorageException e, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message",
                e.getMessage());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "redirect:/user/" + user.getLogin();

    }

}

