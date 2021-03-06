package com.place4code.reddit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ResourceConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload-dir/**").addResourceLocations("file:upload-dir/");
        registry.addResourceHandler("/src/main/resources/upload-dir/**").addResourceLocations("file:src/main/resources/upload-dir/");
    }


}