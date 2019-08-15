package com.place4code.reddit.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImage(Long userId, MultipartFile file);
}
