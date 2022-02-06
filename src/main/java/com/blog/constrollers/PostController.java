package com.blog.constrollers;

import com.blog.entities.PostEntity;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;

import com.blog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Date;

@Controller
@AllArgsConstructor
public class PostController {

    PostRepository postRepository;
    UserRepository userRepository;
    UserService userService;

    @GetMapping("/post/{post_id}")
    public String postPage(@PathVariable Long post_id, Model model) {
        var postEntity = postRepository.findById(post_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var userEntities = userRepository.findAll();

        model.addAttribute("users", userEntities);
        model.addAttribute("post", postEntity);

        return "postPage";
    }

    @GetMapping("/createPost")
    @PreAuthorize("isAuthenticated()")
    public String createGET() {
        return "createPost";
    }

    @PostMapping("/createPost")
    @PreAuthorize("isAuthenticated()")
    public String createPOST(@RequestBody String text, Principal principal) {

        var userEntity = userService.getAuthUserEntity(principal);

        var post = new PostEntity();
        post.setText(text);
        post.setUser(userEntity);
        post.setDate(new Date());
        postRepository.save(post);

        return String.format("redirect:/post/%s", post.getId());
    }
}
