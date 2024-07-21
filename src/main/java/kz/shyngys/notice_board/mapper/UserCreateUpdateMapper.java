package kz.shyngys.notice_board.mapper;

import kz.shyngys.notice_board.dto.UserToCreateUpdateDto;
import kz.shyngys.notice_board.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserCreateUpdateMapper {

    UserCreateUpdateMapper INSTANCE = Mappers.getMapper(UserCreateUpdateMapper.class);

    User toUser(UserToCreateUpdateDto dto);

}
