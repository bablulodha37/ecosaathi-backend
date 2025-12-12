package com.lodha.EcoSaathi.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final FileStorageProperties fileStorageProperties;

    public WebConfig(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1. Properties file se path nikala (Jo /tmp/uploads hoga Render par)
        String pathStr = fileStorageProperties.getUploadDir();
        
        // 2. Usko absolute path banaya taaki Linux/Docker sahi jagah dhunde
        Path uploadDir = Paths.get(pathStr);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        // 3. Spring ko bataya ki images kahan hain
        // Note: "file:" lagana zaroori hai aur end mein "/" hona chahiye
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}