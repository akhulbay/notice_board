package kz.shyngys.notice_board.repository;

import jakarta.persistence.LockModeType;
import kz.shyngys.notice_board.model.db.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BetRepository extends JpaRepository<Bet, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = """
                SELECT t FROM Bet t WHERE t.id = :id
            """)
    Optional<Bet> findBetById(@Param("id") Long id);

    @Query(value = """
                    SELECT *
                    FROM t_bet
                    WHERE expires_at <= :now AND lock_id IS NULL
                    LIMIT 1
            """, nativeQuery = true)
    Optional<Bet> findFirstFinishedBet(@Param("now") LocalDateTime now);

    @Modifying
    @Query(value = """
                    UPDATE t_bet
                    SET lock_id = :lockId
                    WHERE id = :id
                    RETURNING lock_id;
            """, nativeQuery = true)
    String lockFinishedBet(@Param("id") Long id, @Param("lockId") String lockId);


}
