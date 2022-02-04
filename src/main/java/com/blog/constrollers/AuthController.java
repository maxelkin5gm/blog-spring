package com.blog.constrollers;

import com.blog.models.User;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class AuthController {

    UserService userService;
    UserRepository userRepository;

    // регистрация
    @GetMapping("/reg")
    public String formRegistration(Model model) {
        model.addAttribute("userForm", new User());
        return "formRegistration";
    }

    @PostMapping("/reg")
    public String apiRegistration(@ModelAttribute @Valid User userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!userService.createUser(userForm)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return "redirect:/login";
    }


    // логин
    @GetMapping("/login")
    public String formLogin(Model model) {
        var users = userRepository.findAll();
        model.addAttribute("users", users);
        return "loginPage";
    }
}
