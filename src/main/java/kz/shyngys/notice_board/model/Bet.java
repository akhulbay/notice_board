package kz.shyngys.notice_board.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("t_bet")
public class Bet {

    @Id
    private Long id;

    private Long amount;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "user_id")
    private Long userId;

}
