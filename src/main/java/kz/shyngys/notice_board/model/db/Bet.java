package kz.shyngys.notice_board.model.db;

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
@Table(name = "t_bet")
public class Bet {

    /**
     * This ID is the same as ID of {@link Advertisement} which it belongs to.
     */
    @Id
    private Long id;

    private Long amount;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "lock_id")
    private String lockId;

    @Column(name = "user_id")
    private Long userId;

}
