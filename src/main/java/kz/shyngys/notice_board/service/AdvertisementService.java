package kz.shyngys.notice_board.service;

import kz.shyngys.notice_board.dto.filter.AdFilter;
import kz.shyngys.notice_board.dto.read.AdvertisementToReadDto;
import kz.shyngys.notice_board.dto.read.PageResponse;
import kz.shyngys.notice_board.dto.write.AdvertisementToCreateUpdateDto;
import org.springframework.data.domain.Pageable;

public interface AdvertisementService {

    PageResponse<AdvertisementToReadDto> load(Pageable pageable, AdFilter filter);

    AdvertisementToReadDto loadById(Long id);

    Long create(AdvertisementToCreateUpdateDto dto);

    AdvertisementToReadDto update(Long id, AdvertisementToCreateUpdateDto dto);

    void delete(Long id);

}
