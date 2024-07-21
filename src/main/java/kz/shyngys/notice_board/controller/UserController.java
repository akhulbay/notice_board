package kz.shyngys.notice_board.controller;

import kz.shyngys.notice_board.dto.UserToCreateUpdateDto;
import kz.shyngys.notice_board.dto.UserToReadDto;
import kz.shyngys.notice_board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserToReadDto> load(int page, int size) {
        return userService.load(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public UserToReadDto loadById(@PathVariable("id") Long id) {
        return userService.loadById(id);
    }

    @GetMapping("/roles")
    public List<String> loadRoles() {
        return userService.loadRoles();
    }

    @PostMapping
    public Long create(@RequestBody UserToCreateUpdateDto userToCreate) {
        return userService.create(userToCreate);
    }

    @PutMapping("/{id}")
    public UserToReadDto update(@PathVariable("id") Long id,
                                @RequestBody UserToCreateUpdateDto userToCreate) {

        return userService.update(id, userToCreate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}
