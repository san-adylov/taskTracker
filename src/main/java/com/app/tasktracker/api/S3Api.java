package com.app.tasktracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.app.tasktracker.services.impl.S3Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "S3 file API")
@RequestMapping("/api/file")
@CrossOrigin(origins = "*", maxAge = 3600)
public class S3Api {

    private final S3Service s3Service;

    @Operation(summary = "This is upload file ")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, String> upload(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        return s3Service.upload(multipartFile);
    }

    @Operation(summary = "This is delete file ")
    @DeleteMapping
    Map<String, String> delete(@RequestParam String fileLink) {
        return s3Service.delete(fileLink);
    }

    @Operation(summary = "This is get all files")
    @GetMapping
    List<String> listAllFiles() {
        return s3Service.listAllFiles();
    }
}