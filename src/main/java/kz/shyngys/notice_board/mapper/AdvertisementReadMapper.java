package kz.shyngys.notice_board.mapper;

import kz.shyngys.notice_board.dto.read.AdvertisementToReadDto;
import kz.shyngys.notice_board.model.Advertisement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdvertisementReadMapper {

    AdvertisementReadMapper INSTANCE = Mappers.getMapper(AdvertisementReadMapper.class);

    @Mapping(target = "images", ignore = true)
    AdvertisementToReadDto toRead(Advertisement advertisement);

}
