package org.yashgamerx.pokemonboot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.yashgamerx.pokemonboot.dto.UserSignupDto;
import org.yashgamerx.pokemonboot.service.JDBCUserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final JDBCUserService jdbcUserService;

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String getLogoutPage() {
        return "logout";
    }

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignupPage(@RequestBody UserSignupDto userSignupDto){
        var password = userSignupDto.password();
        var confirmPassword = userSignupDto.confirmPassword();
        if(!password.equals(confirmPassword)){
            return "unauthorized";
        }
        var username = userSignupDto.username();
        jdbcUserService.addUser(username, password);
        log.info("Username {} was successfully created",username);
        return "login";
    }
}
