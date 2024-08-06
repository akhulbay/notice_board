package kz.shyngys.notice_board.controller;

import kz.shyngys.notice_board.dto.filter.AdFilter;
import kz.shyngys.notice_board.dto.read.AdvertisementToReadDto;
import kz.shyngys.notice_board.dto.write.AdvertisementToCreateUpdateDto;
import kz.shyngys.notice_board.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/advertisements")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping
    public List<AdvertisementToReadDto> findAll(@RequestParam("size") int size,
                                                @RequestParam("page") int page,
                                                @RequestParam(value = "filer", required = false) AdFilter filter) {

        return advertisementService.load(PageRequest.of(page, size), filter);
    }

    @GetMapping("/{id}")
    public AdvertisementToReadDto findById(@PathVariable("id") Long id) {
        return advertisementService.loadById(id);
    }

    @PostMapping
    public Long create(@RequestBody AdvertisementToCreateUpdateDto dto) {
        return advertisementService.create(dto);
    }

    @PutMapping("/{id}")
    public AdvertisementToReadDto update(@PathVariable("id") Long id,
                                         @RequestBody AdvertisementToCreateUpdateDto dto) {

        return advertisementService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        advertisementService.delete(id);
    }

}
