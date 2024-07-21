package kz.shyngys.notice_board.mapper;

import kz.shyngys.notice_board.dto.UserToReadDto;
import kz.shyngys.notice_board.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserReadMapper {

    UserReadMapper INSTANCE = Mappers.getMapper(UserReadMapper.class);

    @Mapping(target = "password", ignore = true)
    UserToReadDto toRead(User user);

}
