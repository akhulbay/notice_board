package kz.shyngys.notice_board.dto.read;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(fluent = true)
public final class AdvertisementToReadDto {

    private Long id;
    private String title;
    private String description;
    private Long minPrice;
    private LocalDateTime createdAt;
    private String status;
    private List<byte[]> images = new ArrayList<>();

}
