package kz.shyngys.notice_board.repository;

import kz.shyngys.notice_board.model.db.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByName(String name);

    void deleteByName(String name);

    boolean existsByName(String name);

}
