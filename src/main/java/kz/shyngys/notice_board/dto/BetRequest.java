package kz.shyngys.notice_board.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BetRequest {

    @NonNull
    public Long amount;

    @NonNull
    public Long advertisementId;

}
