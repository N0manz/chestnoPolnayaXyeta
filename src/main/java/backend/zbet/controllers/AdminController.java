package backend.zbet.controllers;

import backend.zbet.entity.*;
import backend.zbet.repository.BetRepository;
import backend.zbet.repository.SportEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final SportEventRepository eventRepository;
    private final BetRepository betRepository;

    @Autowired
    public AdminController(SportEventRepository eventRepository, BetRepository betRepository){
        this.eventRepository = eventRepository;
        this.betRepository = betRepository;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("events", eventRepository.findAll());
        model.addAttribute("eventForm", new SportEvent());
        return "admin";
    }

    @PostMapping("/create")
    public String createEvent(@ModelAttribute SportEvent event) {

        event.setResult(EventResult.IN_PROGRESS);
        event.setStatus(EventStatus.ACTIVE);

        eventRepository.save(event);

        return "redirect:/admin";
    }

    @PostMapping("/set-result")
    @Transactional
    public String setResult(@RequestParam Long eventId,
                            @RequestParam EventResult result) {

        SportEvent event = eventRepository.findById(eventId)
                .orElseThrow();

        event.setResult(result);
        event.setStatus(EventStatus.EXPIRED);

        List<Bet> bets = betRepository.findByEventId(eventId);

        for (Bet bet : bets) {

            boolean isWinner =
                    (result == EventResult.WIN_FIRST && bet.getChoice() == BetChoice.FIRST)
                            || (result == EventResult.WIN_SECOND && bet.getChoice() == BetChoice.SECOND);

            if (isWinner) {

                BigDecimal winAmount =
                        bet.getAmount().multiply(bet.getCoefAtMoment());

                AppUser user = bet.getUser();
                user.setBalance(user.getBalance().add(winAmount));

                bet.setStatus(BetStatus.WON);

            } else {
                bet.setStatus(BetStatus.LOST);
            }
        }

        return "redirect:/admin";
    }
}