package kz.shyngys.notice_board.mapper;

import kz.shyngys.notice_board.dto.read.UserToReadDto;
import kz.shyngys.notice_board.model.db.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserReadMapper {

    UserReadMapper INSTANCE = Mappers.getMapper(UserReadMapper.class);

    UserToReadDto toRead(User user);

}
