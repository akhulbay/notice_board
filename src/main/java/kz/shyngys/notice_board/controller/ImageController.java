package kz.shyngys.notice_board.controller;

import kz.shyngys.notice_board.dto.read.ImageData;
import kz.shyngys.notice_board.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{name}")
    public ResponseEntity<?> download(@PathVariable("name") String name) {
        byte[] downloaded = imageService.download(name);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(downloaded);
    }

    @PostMapping
    public ResponseEntity<ImageData> upload(@RequestParam("image") MultipartFile multipartFile) {
        ImageData uploaded = imageService.upload(multipartFile);

        return ResponseEntity.ok(uploaded);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        imageService.delete(id);
    }

}
