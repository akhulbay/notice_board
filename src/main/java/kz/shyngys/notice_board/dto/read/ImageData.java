package kz.shyngys.notice_board.dto.read;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageData {

    public Long id;
    public String name;

    public static ImageData of(Long id, String name) {
        ImageData imageData = new ImageData();

        imageData.id = id;
        imageData.name = name;

        return imageData;
    }

}

