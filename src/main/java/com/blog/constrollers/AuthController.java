package com.blog.constrollers;

import com.blog.entities.UserEntity;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        model.addAttribute("userForm", new UserEntity());
        return "formRegistration";
    }

    @PostMapping("/reg")
    public String registration(@ModelAttribute("userForm") @Valid UserEntity userEntity, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "formRegistration";
        }

        var isRegistered = userService.register(userEntity);
        if (!isRegistered) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return "redirect:/login";
    }


    // логин
    @GetMapping("/login")
    public String formLogin(Model model) {
        var userEntities = userRepository.findAll();
        model.addAttribute("users", userEntities);
        return "loginPage";
    }
}
