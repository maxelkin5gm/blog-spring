package com.blog.constrollers;

import com.blog.models.Post;
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
import java.util.Date;
import java.util.Formatter;

@Controller
@AllArgsConstructor
public class PostController {

    PostRepository postRepository;
    UserRepository userRepository;

    @GetMapping("/post/{post_id}")
    public String postPage(@PathVariable Long post_id, Model model) {
        var post = postRepository.findById(post_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var users = userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("post", post);

        return "postPage";
    }

    @GetMapping("/createPost")
    public String createGET() {
        return "createPost";
    }

    @PostMapping("/createPost")
    public String createPOST(@RequestBody String text, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        var user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));

        var post = new Post();
        post.setText(text);
        post.setUser(user);
        post.setDate(new Date());
        postRepository.save(post);

        //noinspection SpringMVCViewInspection
        return "redirect:/post/" + post.getId();
    }
}
