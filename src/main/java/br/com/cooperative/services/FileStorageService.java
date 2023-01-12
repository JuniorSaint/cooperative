package br.com.cooperative.services;


import br.com.cooperative.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileStorageService {
    @Value("${file.upload-dir:pathImage}")
    private String pathImage = "pathImage";

    private Path path = Paths.get(pathImage).toAbsolutePath().normalize();

    @Autowired
    public FileStorageService() {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new FileStorageException("Could not create the directory where the upload files will be storage ");
        }
    }

    public String storageFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new FileStorageException("Sorry, the name of file contains '..' rename and try again : " + fileName);
        }
        Path targetLocation = path.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}
