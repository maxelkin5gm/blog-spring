package com.blog.constrollers;

import com.blog.models.Comment;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Date;

@Controller
@AllArgsConstructor
public class CommentController {

    CommentRepository commentRepository;
    UserRepository userRepository;
    PostRepository postRepository;

    @PostMapping("/createComment")
    public String createComment(@RequestBody JsonComment req, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        var user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));

        var post = postRepository.findById(req.post_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var comment = new Comment();
        comment.setOwner(user);
        comment.setPost(post);
        comment.setText(req.text);
        comment.setDate(new Date());
        commentRepository.save(comment);

        //noinspection SpringMVCViewInspection
        return "redirect:/post/" + post.getId();
    }

    @Data
    static class JsonComment {
        String text;
        Long post_id;
    }
}
