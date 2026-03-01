package backend.zbet.controllers;

import backend.zbet.entity.AppUser;
import backend.zbet.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Auth", description = "Эндпойнты для аунтификации")
@Controller
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @Operation(summary = "Страница Логина")
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @Operation(summary = "Страница Регистрации")
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @Operation(summary = "Регистрация нового юзера")
    @PostMapping("/register")
    public String register(@ModelAttribute AppUser user) {
        authService.register(user);
        return "redirect:/login";
    }
}
