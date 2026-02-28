package backend.zbet.service;

import backend.zbet.entity.*;
import backend.zbet.repository.AppUserRepository;
import backend.zbet.repository.BetRepository;
import backend.zbet.repository.SportEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BetService {

    private final BetRepository betRepository;
    private final SportEventRepository eventRepository;
    private final AppUserRepository userRepository;

    public BetService(BetRepository betRepository, SportEventRepository eventRepository, AppUserRepository userRepository){
        this.betRepository = betRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void placeBet(Long eventId,
                         BetChoice choice,
                         BigDecimal amount,
                         Long userId) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Сумма должна быть больше 0");
        }

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        SportEvent event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Событие не найдено"));

        if (event.getStatus() != EventStatus.ACTIVE
                || event.getResult() != EventResult.IN_PROGRESS) {
            throw new RuntimeException("Ставки на это событие закрыты");
        }

        if (user.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Недостаточно средств");
        }

        // списываем баланс
        user.setBalance(user.getBalance().subtract(amount));

        BigDecimal coef = (choice == BetChoice.FIRST)
                ? event.getCoefFirst()
                : event.getCoefSecond();

        Bet bet = new Bet();
        bet.setUser(user);
        bet.setEvent(event);
        bet.setChoice(choice);
        bet.setAmount(amount);
        bet.setCoefAtMoment(coef);
        bet.setStatus(BetStatus.PENDING);
        bet.setCreatedAt(LocalDateTime.now());

        betRepository.save(bet);
    }

    public List<Bet> findUserBets(Long userId){
        return betRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
