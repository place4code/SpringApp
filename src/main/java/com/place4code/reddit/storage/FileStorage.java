package com.place4code.reddit.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {

    public void delete(String fileName);

    public void store(MultipartFile file);
}