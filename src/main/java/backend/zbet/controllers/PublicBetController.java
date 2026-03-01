package backend.zbet.controllers;

import backend.zbet.entity.Bet;
import backend.zbet.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Public Bets", description = "Отображение всех ставок пользователей")
@Controller
public class PublicBetController {

    private final BetRepository betRepository;

    @Autowired
    public PublicBetController(BetRepository betRepository){
        this.betRepository = betRepository;
    }

    @Operation(summary = "All bets",
            description = "Возвращает все ставки пользователей в порядке даты")
    @GetMapping("/bets")
    public String allBets(Model model) {

        List<Bet> bets = betRepository.findAllByOrderByCreatedAtDesc();

        model.addAttribute("bets", bets);

        return "all-bets";
    }
}