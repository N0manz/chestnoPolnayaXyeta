package backend.zbet.repository;

import backend.zbet.entity.EventStatus;
import backend.zbet.entity.SportEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportEventRepository extends JpaRepository<SportEvent, Long> {

    List<SportEvent> findByStatus(EventStatus status);
}
