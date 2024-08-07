package kz.shyngys.notice_board.controller;

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

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void makeBet(@RequestBody BetRequest request) {
        noticeBoardService.makeBet(request);
    }

}
