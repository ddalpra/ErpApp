package it.ddalpra.acme.erpApp.config;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            // Se l'utente è autenticato, aggiungi i suoi dettagli al modello
            model.addAttribute("username", principal.getPreferredUsername());
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "index"; // Ritorna il template index.html
    }

    // DEVE esistere un metodo che gestisca l'URL "/dashboard"
    @GetMapping("/dashboard") 
    public String dashboardPage(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("fullName", principal.getFullName());
            model.addAttribute("email", principal.getEmail());
            model.addAttribute("idToken", principal.getIdToken().getTokenValue());
            return "dashboard"; 
        } else {
            model.addAttribute("isAuthenticated", false);
            return "index"; 
        } 
       // Ritorna il nome del template Thymeleaf
    }

    @GetMapping("/protected")
    public String protectedPage(@AuthenticationPrincipal OidcUser principal, Model model) {
        model.addAttribute("fullName", principal.getFullName());
        model.addAttribute("email", principal.getEmail());
        model.addAttribute("idToken", principal.getIdToken().getTokenValue());
        return "protected"; // Ritorna il template protected.html
    }
}