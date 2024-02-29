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
import jakarta.validation.constraints.NotNull;

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

    public String storeFile(@NotNull MultipartFile file) {
        final var orignalFileName = file.getOriginalFilename();
        if (orignalFileName == null) {
            throw new NullPointerException("m=FileStorageService.storeFile orignalFileName is null");
        }
        final String filename = StringUtils.cleanPath(orignalFileName);
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
            final var uri = filePath.toUri();
            if (uri == null) {
                throw new NullPointerException("m=FileStorageService.loadFileAsResource orignalFileName is uri");
            }

            final Resource resource = new UrlResource(uri);
            if (resource.exists()) {
                return resource;
            }
            throw new MyFileNotFoundException("File not found");
        } catch (Exception e) {
            throw new MyFileNotFoundException("File not found", e);
        }
    }

}
