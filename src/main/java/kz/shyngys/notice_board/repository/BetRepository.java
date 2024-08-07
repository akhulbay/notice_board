package kz.shyngys.notice_board.repository;

import jakarta.persistence.LockModeType;
import kz.shyngys.notice_board.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BetRepository extends JpaRepository<Bet, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = """
                SELECT * FROM t_bet WHERE id = :id
            """, nativeQuery = true)
    Optional<Bet> findBetById(@Param("id") Long id);

}
