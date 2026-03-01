package backend.zbet.controllers;

import backend.zbet.entity.AppUser;
import backend.zbet.entity.Bet;
import backend.zbet.entity.BetChoice;
import backend.zbet.service.BetService;
import backend.zbet.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Bets", description = "Управление ставками")
@Controller
public class BetController {

    private final BetService betService;

    @Autowired
    public BetController(BetService betService){
        this.betService = betService;
    }

    @Operation(summary = "Place a bet",
            description = "Установить ставку для авторизованного юзера")
    @PostMapping("/bet")
    public String placeBet(@Parameter(description = "Id события") @RequestParam Long eventId,
                           @Parameter(description = "Выбор на победу Первой/Второй команды") @RequestParam BetChoice choice,
                           @Parameter(description = "Сумма ставки") @RequestParam BigDecimal amount,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {

        try {
            CustomUserDetails userDetails =
                    (CustomUserDetails) authentication.getPrincipal();

            Long userId = userDetails.getUser().getId();

            betService.placeBet(eventId, choice, amount, userId);

            redirectAttributes.addFlashAttribute("success",
                    "Ставка успешно принята");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    e.getMessage());
        }

        return "redirect:/";
    }

    @Operation(summary = "User bets",
            description = "возвращает все ставки конкретного пользователя")
    @GetMapping("/my-bets")
    public String myBets(Model model, Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Long userId = userDetails.getUser().getId();

        List<Bet> bets = betService.findUserBets(userId);

        model.addAttribute("bets", bets);

        return "my-bets";
    }
}