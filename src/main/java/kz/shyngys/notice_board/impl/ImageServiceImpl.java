package kz.shyngys.notice_board.impl;

import kz.shyngys.notice_board.dto.read.ImageData;
import kz.shyngys.notice_board.exception.NoAdvertisementWithId;
import kz.shyngys.notice_board.exception.NoImageWithId;
import kz.shyngys.notice_board.exception.NoImageWithName;
import kz.shyngys.notice_board.exception.NoUserWithId;
import kz.shyngys.notice_board.model.db.Advertisement;
import kz.shyngys.notice_board.model.db.Image;
import kz.shyngys.notice_board.model.db.User;
import kz.shyngys.notice_board.repository.AdvertisementRepository;
import kz.shyngys.notice_board.repository.ImageRepository;
import kz.shyngys.notice_board.repository.UserRepository;
import kz.shyngys.notice_board.service.AuthService;
import kz.shyngys.notice_board.service.ImageService;
import kz.shyngys.notice_board.util.ImageUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

import static kz.shyngys.notice_board.util.StrUtils.isNullOrEmpty;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;

    @Override
    public ImageData upload(MultipartFile imageFile) {
        Long userId = authService.getCurrentUserId()
                .orElseThrow(() -> new RuntimeException("User is not logged in"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoUserWithId(userId));

        String imageName = imageFile.getName() + "_" + user.getUsername();

        return upload(imageFile, imageName, null, null);
    }

    @Override
    public ImageData upload(MultipartFile imageFile, String name) {
        return upload(imageFile, name, null, null);
    }

    @Transactional
    @Override
    public ImageData upload(MultipartFile imageFile, String name, Long adId, Integer orderIndex) {
        try {
            Image image = Image.builder()
                    .type(imageFile.getContentType())
                    .name(name)
                    .content(ImageUtils.compressImage(imageFile.getBytes()))
                    .advertisement(getAdvertisement(adId))
                    .orderIndex(orderIndex)
                    .build();

            Image savedImage = imageRepository.saveAndFlush(image);

            return ImageData.of(savedImage.getId(), name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Advertisement getAdvertisement(Long adId) {
        if (adId == null) {
            return null;
        }

        return advertisementRepository.findById(adId)
                .orElseThrow(() -> new NoAdvertisementWithId(adId));
    }

    @Override
    public byte[] download(String name) {
        if (isNullOrEmpty(name)) {
            throw new IllegalArgumentException("image name cannot be null or empty");
        }

        Image image = imageRepository.findByName(name)
                .orElseThrow(() -> new NoImageWithName(name));

        try {
            return ImageUtils.decompressImage(image.getContent());
        } catch (DataFormatException | IOException e) {
            throw new ContextedRuntimeException("Error downloading image", e)
                    .addContextValue("Image ID", image.getId())
                    .addContextValue("Image Name", image.getName());
        }
    }

    @Override
    public Image downloadById(@NonNull Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new NoImageWithId(id));
    }

    @Override
    public void delete(String name) {
        if (isNullOrEmpty(name)) {
            throw new IllegalArgumentException("image name cannot be null or empty");
        }

        imageRepository.deleteByName(name);
    }

    @Override
    public void delete(@NonNull Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        if (isNullOrEmpty(name)) {
            throw new IllegalArgumentException("image name cannot be null or empty");
        }

        return imageRepository.existsByName(name);
    }

}

