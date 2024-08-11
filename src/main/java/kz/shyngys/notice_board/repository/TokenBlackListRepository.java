package kz.shyngys.notice_board.repository;

import kz.shyngys.notice_board.model.db.BlackListedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlackListRepository extends JpaRepository<BlackListedToken, Long> {

    boolean existsByToken(String token);

}
