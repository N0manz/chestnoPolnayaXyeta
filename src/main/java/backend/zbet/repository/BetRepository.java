package backend.zbet.repository;

import backend.zbet.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

    List<Bet> findByEventId(Long eventId);

    List<Bet> findByUserId(Long userId);

    List<Bet> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Bet> findAllByOrderByCreatedAtDesc();
}
