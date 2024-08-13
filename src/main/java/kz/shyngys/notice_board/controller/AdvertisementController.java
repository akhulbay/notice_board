package kz.shyngys.notice_board.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.shyngys.notice_board.dto.filter.AdFilter;
import kz.shyngys.notice_board.dto.read.AdvertisementToReadDto;
import kz.shyngys.notice_board.dto.read.PageResponse;
import kz.shyngys.notice_board.dto.write.AdvertisementToCreateUpdateDto;
import kz.shyngys.notice_board.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/advertisements")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @Operation(
            summary = "Retrieve a paginated list of advertisements",
            description = "This endpoint returns a paginated list of advertisements. " +
                          "You can optionally apply filters to narrow down the results. " +
                          "Specify the page number and size as query parameters, and an optional filter for advanced search."
    )
    @GetMapping
    public PageResponse<AdvertisementToReadDto> findAll(@RequestParam("size") int size,
                                                        @RequestParam("page") int page,
                                                        @RequestBody AdFilter filter) {
        return advertisementService.load(PageRequest.of(page, size), filter);
    }

    @Operation(
            summary = "Retrieve an advertisement by ID",
            description = "This endpoint fetches the details of a specific advertisement by its unique ID."
    )
    @GetMapping("/{id}")
    public AdvertisementToReadDto findById(@PathVariable("id") Long id) {
        return advertisementService.loadById(id);
    }

    @Operation(
            summary = "Create a new advertisement",
            description = "This endpoint creates a new advertisement with the provided details. " +
                          "The request body should contain all necessary information for the advertisement."
    )
    @PostMapping
    public Long create(@RequestBody AdvertisementToCreateUpdateDto dto) {
        return advertisementService.create(dto);
    }

    @Operation(
            summary = "Update an existing advertisement by ID",
            description = "This endpoint updates the details of an existing advertisement identified by its unique ID. " +
                          "The request body should contain the updated advertisement information."
    )
    @PutMapping("/{id}")
    public AdvertisementToReadDto update(@PathVariable("id") Long id,
                                         @RequestBody AdvertisementToCreateUpdateDto dto) {
        return advertisementService.update(id, dto);
    }

    @Operation(
            summary = "Delete an advertisement by ID",
            description = "This endpoint deletes an advertisement identified by its unique ID. " +
                          "The advertisement will be permanently removed from the system."
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        advertisementService.delete(id);
    }

}
