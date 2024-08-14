package kz.shyngys.notice_board.service;

import kz.shyngys.notice_board.dto.read.ImageData;
import kz.shyngys.notice_board.model.db.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ImageData upload(MultipartFile imageFile);

    ImageData upload(MultipartFile imageFile, String name);

    ImageData upload(MultipartFile imageFile, String name, Long profileId, Integer orderIndex);

    byte[] download(String name);

    Image downloadById(Long id);

    void delete(String name);

    void delete(Long id);

    boolean existsByName(String name);

}
