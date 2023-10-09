package br.com.athenas.desafio.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.athenas.desafio.config.FileStorageConfig;
import br.com.athenas.desafio.exceptions.FileStorageException;
import br.com.athenas.desafio.exceptions.MyFileNotFoundException;

@Service
public class FileStorageService {

    private final Path fileStoregeLocation;

    @Autowired
    public FileStorageService(final FileStorageConfig fileStorageConfig) {
        final Path path = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();
        this.fileStoregeLocation = path;

        try {
            Files.createDirectories(this.fileStoregeLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create the directory where the upload files will be stored!", e);
        }
    }

    public String storeFile(MultipartFile file) {
        final String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (filename.contains("..")) {
                throw new FileStorageException("Sorry! Filename cotains invalid path sequence " + filename);
            }

            final Path targetLocation = this.fileStoregeLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return filename;
        } catch (Exception e) {
            throw new FileStorageException("Could not store file " + filename + ". Please try again!", e);
        }
    }

    public Resource loadFileAsResource(String filename) {
        try {
            final Path filePath = this.fileStoregeLocation.resolve(filename).normalize();
            final Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found");
            }
        } catch (Exception e) {
            throw new MyFileNotFoundException("File not found", e);
        }
    }

}
