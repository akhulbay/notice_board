package kz.shyngys.notice_board.mapper;

import kz.shyngys.notice_board.dto.write.AdvertisementToCreateUpdateDto;
import kz.shyngys.notice_board.model.Advertisement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdvertisementCreateUpdateMapper {

    AdvertisementCreateUpdateMapper INSTANCE = Mappers.getMapper(AdvertisementCreateUpdateMapper.class);

    @Mapping(target = "images", ignore = true)
    Advertisement toAdvertisement(AdvertisementToCreateUpdateDto dto);

}
