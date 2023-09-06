package com.marko.kladionicajava.controller;


import com.marko.kladionicajava.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/administrator")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    @PostMapping("upload/image")
    public String uploadImage(@RequestParam("image") MultipartFile file,
                                         @RequestParam("email") String email) {
        fileService.uploadImage(file, email);
        return null;
    }

    @GetMapping("download/image/{fileName}")
    public String showImage(@PathVariable String fileName) {
        fileService.retrieveImage(fileName);
        return null;
    }
}
