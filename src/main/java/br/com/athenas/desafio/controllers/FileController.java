package br.com.athenas.desafio.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.athenas.desafio.records.UploadFileResponseRecord;
import br.com.athenas.desafio.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/file")
@Tag(name = "File Endpoint", description = "Endpoints for managers Files")
public class FileController {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileController(final FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/uploadFile")
    public UploadFileResponseRecord uploadFile(
            @RequestParam("file") @NotNull MultipartFile file) {

        final var filename = fileStorageService.storeFile(file);
        if (filename == null) {
            throw new NullPointerException("m=FileController.uploadFile filename is null");
        }

        final String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/file/downloadFile")
                .path(filename)
                .toUriString();

        return new UploadFileResponseRecord(
                filename, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadFileMultipleFiles")
    public List<UploadFileResponseRecord> uploadFileMultipleFiles(
            @RequestParam("files") MultipartFile[] files) {

        return Arrays.asList(files)
                .stream()
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String filename,
            HttpServletRequest request) throws IOException {

        final Resource resource = this.fileStorageService.loadFileAsResource(filename);
        String contentType = "";
        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType.isBlank()) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

}
