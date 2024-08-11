package kz.shyngys.notice_board.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.shyngys.notice_board.dto.write.UserToCreateUpdateDto;
import kz.shyngys.notice_board.dto.read.UserToReadDto;
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

    @Operation(
            summary = "Retrieve a paginated list of users",
            description = "This endpoint returns a paginated list of all users in the system. " +
                          "The page number and size should be specified as query parameters."
    )
    @GetMapping
    public List<UserToReadDto> load(@RequestParam int page, @RequestParam int size) {
        return userService.load(PageRequest.of(page, size));
    }

    @Operation(
            summary = "Retrieve a user by ID",
            description = "This endpoint fetches the details of a specific user by their unique ID."
    )
    @GetMapping("/{id}")
    public UserToReadDto loadById(@PathVariable("id") Long id) {
        return userService.loadById(id);
    }

    @Operation(
            summary = "Retrieve all available roles",
            description = "This endpoint returns a list of all roles defined within the application."
    )
    @GetMapping("/roles")
    public List<String> loadRoles() {
        return userService.loadRoles();
    }

    @Operation(
            summary = "Create a new user",
            description = "This endpoint creates a new user with the provided details. " +
                          "The request body should contain all necessary information for the new user."
    )
    @PostMapping
    public Long create(@RequestBody UserToCreateUpdateDto userToCreate) {
        return userService.create(userToCreate);
    }

    @Operation(
            summary = "Update an existing user by ID",
            description = "This endpoint updates the details of an existing user identified by their unique ID. " +
                          "The request body should contain the updated user information."
    )
    @PutMapping("/{id}")
    public UserToReadDto update(@PathVariable("id") Long id,
                                @RequestBody UserToCreateUpdateDto userToCreate) {
        return userService.update(id, userToCreate);
    }

    @Operation(
            summary = "Delete a user by ID",
            description = "This endpoint deletes a user identified by their unique ID. " +
                          "The user will be permanently removed from the system."
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}
