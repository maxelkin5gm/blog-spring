package com.blog.constrollers;

import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;

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

    // test
    @GetMapping("/test")
    public String test(Principal principal) {

        return "test";
    }


    @GetMapping("/")
    public String helloGET(Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        var user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));

        return "redirect:/" + user.getId();
    }

    @GetMapping("/{user_id}")
    public String homePage(@PathVariable Long user_id, Model model, Principal principal) {

        // нужны тесты

        var user = userRepository.findById(user_id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var posts = postRepository.findByUser(user);
        var users = userRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("users", users);

        if (principal != null && principal.getName().equals(user.getEmail())) {
            return "profilePage";
        } else {
            return "mainPage";
        }
    }


}
