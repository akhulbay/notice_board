package kz.shyngys.notice_board.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.shyngys.notice_board.dto.BetRequest;
import kz.shyngys.notice_board.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice-board")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    @Operation(
            summary = "Make a bet for a specific advertisement",
            description = "All fields must be non null."
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void makeBet(@RequestBody BetRequest request) {
        noticeBoardService.makeBet(request);
    }

}
