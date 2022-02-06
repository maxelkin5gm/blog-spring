package com.blog.constrollers;

import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;

import com.blog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

/**
 * @author Maxim Elkin
 */
@Controller
@AllArgsConstructor
public class MainController {

    UserRepository userRepository;
    PostRepository postRepository;
    UserService userService;

    // test
    @GetMapping("/test")
    public String test(Principal principal) {
        var userEntity = userService.getAuthUserEntity(principal);

        return "test";
    }


    @GetMapping("/")
    public String main(Principal principal) {

        var userEntity = userService.getAuthUserEntity(principal);

        return String.format("redirect:/%s", userEntity.getId());
    }

    @GetMapping("/{user_id}")
    public String homePage(@PathVariable Long user_id, Principal principal, Model model) {

        var userEntity = userRepository.findById(user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var postEntities = postRepository.findByUser(userEntity);
        var userEntities = userRepository.findAll();

        model.addAttribute("user", userEntity);
        model.addAttribute("posts", postEntities);
        model.addAttribute("users", userEntities);

        if (principal != null && principal.getName().equals(userEntity.getEmail())) {
            return "profilePage";
        } else {
            return "mainPage";
        }
    }


}
