package backend.zbet.controllers;

import backend.zbet.entity.Bet;
import backend.zbet.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class PublicBetController {

    private final BetRepository betRepository;

    @Autowired
    public PublicBetController(BetRepository betRepository){
        this.betRepository = betRepository;
    }

    @GetMapping("/bets")
    public String allBets(Model model) {

        List<Bet> bets = betRepository.findAllByOrderByCreatedAtDesc();

        model.addAttribute("bets", bets);

        return "all-bets";
    }
}