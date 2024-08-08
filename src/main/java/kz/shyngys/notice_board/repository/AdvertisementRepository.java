package kz.shyngys.notice_board.repository;

import kz.shyngys.notice_board.model.db.AdStatus;
import kz.shyngys.notice_board.model.db.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>,
        JpaSpecificationExecutor<Advertisement> {

    Page<Advertisement> findAllByStatusEquals(AdStatus status, Pageable pageable);

}
