package backend.zbet.controllers;

import backend.zbet.entity.AppUser;
import backend.zbet.entity.EventStatus;
import backend.zbet.entity.SportEvent;
import backend.zbet.repository.SportEventRepository;
import backend.zbet.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final SportEventRepository eventRepository;

    @Autowired
    public HomeController(SportEventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        AppUser user = userDetails.getUser();

        List<SportEvent> activeEvents =
                eventRepository.findByStatus(EventStatus.ACTIVE);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("balance", user.getBalance());
        model.addAttribute("role", user.getRole());
        model.addAttribute("events", activeEvents);

        return "home";
    }
}
