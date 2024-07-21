package kz.shyngys.notice_board.impl;


import kz.shyngys.notice_board.dto.write.UserToCreateUpdateDto;
import kz.shyngys.notice_board.dto.read.UserToReadDto;
import kz.shyngys.notice_board.exception.NoUserWithId;
import kz.shyngys.notice_board.mapper.UserCreateUpdateMapper;
import kz.shyngys.notice_board.mapper.UserReadMapper;
import kz.shyngys.notice_board.model.User;
import kz.shyngys.notice_board.model.UserRole;
import kz.shyngys.notice_board.repository.UserRepository;
import kz.shyngys.notice_board.service.UserService;
import kz.shyngys.notice_board.util.validator.UserValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static kz.shyngys.notice_board.util.StrUtil.isNotNullAndEmpty;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        format("7mBGaPdO03 :: user with email %s does not exists", username)));
    }

    @Override
    public List<UserToReadDto> load(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserReadMapper.INSTANCE::toRead)
                .toList();
    }

    @Override
    public UserToReadDto loadById(@NonNull Long id) {
        return userRepository.findById(id)
                .map(UserReadMapper.INSTANCE::toRead)
                .orElseThrow(() -> new NoUserWithId(id));
    }

    @Override
    public List<String> loadRoles() {
        return Arrays.stream(UserRole.values())
                .map(UserRole::name)
                .toList();
    }

    @Override
    public Long create(@NonNull UserToCreateUpdateDto userToCreate) {
        UserValidator.validate(userToCreate);

        User user = UserCreateUpdateMapper.INSTANCE.toUser(userToCreate);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.saveAndFlush(user).getId();
    }

    @Override
    public UserToReadDto update(@NonNull Long id, @NonNull UserToCreateUpdateDto userToUpdate) {
        return userRepository.findById(id)
                .map(user -> {
                    setUpdates(userToUpdate, user);
                    return userRepository.save(user);
                })
                .map(UserReadMapper.INSTANCE::toRead)
                .orElseThrow(() -> new NoUserWithId(id));
    }

    private void setUpdates(UserToCreateUpdateDto userToUpdate, User user) {
        if (isNotNullAndEmpty(userToUpdate.email())) {
            user.setEmail(userToUpdate.email());
        }

        if (isNotNullAndEmpty(userToUpdate.password())) {
            user.setPassword(userToUpdate.password());
        }

        if (isNotNullAndEmpty(userToUpdate.role())) {
            user.setRole(UserRole.valueOf(userToUpdate.role()));
        }
    }

    @Override
    public void delete(@NonNull Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean isExistsByEmail(@NonNull String email) {
        return userRepository.existsByEmail(email);
    }

}
