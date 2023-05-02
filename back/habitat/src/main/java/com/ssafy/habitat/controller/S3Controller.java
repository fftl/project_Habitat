package com.ssafy.habitat.controller;

import com.ssafy.habitat.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/s3")
public class S3Controller {
    private final S3Uploader s3Uploader;

    @PostMapping("/image")
    public ResponseEntity updateUserImage(@RequestParam("file") MultipartFile file) {
        try {
            s3Uploader.upload(file, "static");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}