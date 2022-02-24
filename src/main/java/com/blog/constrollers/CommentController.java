package com.blog.constrollers;

import com.blog.entities.CommentEntity;
import com.blog.dao.Comment;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;

import com.blog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Date;

@Controller
@AllArgsConstructor
public class CommentController {

    CommentRepository commentRepository;
    PostRepository postRepository;
    UserService userService;


    @PostMapping("/createComment")
    @PreAuthorize("isAuthenticated()")
    public String createComment(@RequestBody Comment comment, Principal principal) {

        var userEntity = userService.getAuthUserEntity(principal);

        var postEntity = postRepository.findById(comment.getPost_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var commentEntity = new CommentEntity();
        commentEntity.setOwner(userEntity);
        commentEntity.setPost(postEntity);
        commentEntity.setText(comment.getText());
        commentEntity.setDate(new Date());
        commentRepository.save(commentEntity);

        return String.format("redirect:/post/%s", postEntity.getId());
    }
}
