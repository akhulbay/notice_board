package kz.shyngys.notice_board.impl;

import kz.shyngys.notice_board.dto.filter.AdFilter;
import kz.shyngys.notice_board.dto.read.AdvertisementToReadDto;
import kz.shyngys.notice_board.dto.write.AdvertisementToCreateUpdateDto;
import kz.shyngys.notice_board.exception.NoAdvertisementWithId;
import kz.shyngys.notice_board.mapper.AdvertisementCreateUpdateMapper;
import kz.shyngys.notice_board.mapper.AdvertisementReadMapper;
import kz.shyngys.notice_board.model.Advertisement;
import kz.shyngys.notice_board.repository.AdvertisementRepository;
import kz.shyngys.notice_board.service.AdvertisementService;
import kz.shyngys.notice_board.util.validator.AdvertisementValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kz.shyngys.notice_board.util.StrUtil.isNotNullAndEmpty;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    @Override
    public List<AdvertisementToReadDto> load(@NonNull Pageable pageable, AdFilter filter) {
        return List.of();
    }

    @Override
    public AdvertisementToReadDto loadById(@NonNull Long id) {
        return advertisementRepository.findById(id)
                .map(AdvertisementReadMapper.INSTANCE::toRead)
                .orElseThrow(() -> new NoAdvertisementWithId(id));
    }

    @Transactional
    @Override
    public Long create(@NonNull AdvertisementToCreateUpdateDto dto) {
        AdvertisementValidator.validate(dto);

        Advertisement advertisement = AdvertisementCreateUpdateMapper.INSTANCE.toAdvertisement(dto);

        return advertisementRepository.save(advertisement).getId();
    }

    @Transactional
    @Override
    public AdvertisementToReadDto update(@NonNull Long id, @NonNull AdvertisementToCreateUpdateDto dto) {
        return advertisementRepository.findById(id).stream()
                .peek(advertisement -> setUpdates(advertisement, dto))
                .map(advertisementRepository::saveAndFlush)
                .map(AdvertisementReadMapper.INSTANCE::toRead)
                .findFirst()
                .orElseThrow(() -> new NoAdvertisementWithId(id));
    }

    private void setUpdates(Advertisement advertisement, AdvertisementToCreateUpdateDto dto) {
        if (isNotNullAndEmpty(dto.title())) {
            advertisement.setTitle(dto.title());
        }

        if (isNotNullAndEmpty(dto.description())) {
            advertisement.setDescription(dto.description());
        }

        if (dto.createdAt() != null) {
            advertisement.setCreatedAt(dto.createdAt());
        }

        if (dto.minPrice() != null) {
            advertisement.setMinPrice(dto.minPrice());
        }
    }

}
