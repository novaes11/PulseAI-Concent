package com.novaes.pulseAIConcent.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(Model model){
        // Retorna a view 'templates/auth/login.html'
        return "auth/login";
    }

    @GetMapping("/criar-conta")
    public String criarConta(Model model){
        //Retorna a view 'templates/auth/criar-conta'
        return "auth/criar-conta";
    }
}
