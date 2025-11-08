package com.kesi.planit.test;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/test/file")
@Slf4j
public class TestFileController {
    @PostMapping
    public void uploadFile(@RequestParam("files") List<MultipartFile> files) throws IOException {
        log.info("call size {}", files.size());
        for (MultipartFile file : files) {
            log.info("Uploading file {}, size {}", file.getOriginalFilename(), file.getBytes().length);
        }
    }
}
