package kz.shyngys.notice_board.repository;

import kz.shyngys.notice_board.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);

}
