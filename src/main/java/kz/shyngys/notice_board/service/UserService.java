package kz.shyngys.notice_board.service;

import kz.shyngys.notice_board.dto.UserToCreateUpdateDto;
import kz.shyngys.notice_board.dto.UserToReadDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserToReadDto> load(Pageable pageable);

    UserToReadDto loadById(Long id);

    List<String> loadRoles();

    Long create(UserToCreateUpdateDto userToCreate);

    UserToReadDto update(Long id, UserToCreateUpdateDto userToUpdate);

    void delete(Long id);

    boolean isExistsByEmail(String email);

}
