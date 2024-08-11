package kz.shyngys.notice_board.impl;

import kz.shyngys.notice_board.dto.filter.AdFilter;
import kz.shyngys.notice_board.dto.read.AdvertisementToReadDto;
import kz.shyngys.notice_board.dto.read.PageResponse;
import kz.shyngys.notice_board.dto.write.AdvertisementToCreateUpdateDto;
import kz.shyngys.notice_board.exception.NoAdvertisementWithId;
import kz.shyngys.notice_board.exception.NoUserWithId;
import kz.shyngys.notice_board.mapper.AdvertisementCreateUpdateMapper;
import kz.shyngys.notice_board.mapper.AdvertisementReadMapper;
import kz.shyngys.notice_board.model.db.AdStatus;
import kz.shyngys.notice_board.model.db.Advertisement;
import kz.shyngys.notice_board.model.db.User;
import kz.shyngys.notice_board.repository.AdvertisementRepository;
import kz.shyngys.notice_board.repository.UserRepository;
import kz.shyngys.notice_board.service.AdvertisementService;
import kz.shyngys.notice_board.service.AuthService;
import kz.shyngys.notice_board.specification.AdSpecification;
import kz.shyngys.notice_board.util.validator.AdvertisementValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static kz.shyngys.notice_board.util.StrUtil.isNotNullAndEmpty;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Override
    public PageResponse<AdvertisementToReadDto> load(@NonNull Pageable pageable, AdFilter filter) {
        Page<Advertisement> page = filter == null
                ? advertisementRepository.findAllByStatusEquals(AdStatus.ACTIVE, pageable)
                : advertisementRepository.findAll(AdSpecification.withFilter(filter), pageable);

        return PageResponse.of(page, AdvertisementReadMapper.INSTANCE::toRead);
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

        setDefaults(advertisement);

        return advertisementRepository.save(advertisement).getId();
    }

    private void setDefaults(Advertisement advertisement) {
        if (advertisement.getStatus() == null) {
            advertisement.setStatus(AdStatus.ACTIVE);
        }

        if (advertisement.getCreatedAt() == null) {
            advertisement.setCreatedAt(LocalDateTime.now());
        }

        if (advertisement.getUser() == null) {
            Long currentUserId = authService.getCurrentUserId()
                    .orElseThrow(() -> new RuntimeException("User is not logged in"));

            User user = userRepository.findById(currentUserId)
                    .orElseThrow(() -> new NoUserWithId(currentUserId));

            advertisement.setUser(user);
        }
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

    @Transactional
    @Override
    public void delete(Long id) {
        advertisementRepository.deleteById(id);
    }

}
