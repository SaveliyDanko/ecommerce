package com.savadanko.ecommerce.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    @Value("${project.image}")
    private String path;

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Failed to store empty file.");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("File must have a name");
        }

        String cleanFileName = StringUtils.cleanPath(originalFilename);
        if (cleanFileName.contains("..")) {
            throw new SecurityException("Cannot store file with relative path outside current directory " + originalFilename);
        }

        String extension = "";
        int dotIndex = cleanFileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = cleanFileName.substring(dotIndex);
        }

        String fileName = UUID.randomUUID() + extension;

        Path uploadDir = Paths.get(path).toAbsolutePath().normalize();

        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path targetLocation = uploadDir.resolve(fileName);
            file.transferTo(targetLocation);
            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + fileName, e);
        }
    }
}
