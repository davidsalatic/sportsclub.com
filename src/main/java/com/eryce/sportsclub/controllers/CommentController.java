package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Authorize;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.dto.CommentRequestDTO;
import com.eryce.sportsclub.models.Comment;
import com.eryce.sportsclub.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routes.COMMENT_BASE)
@PreAuthorize(Authorize.HAS_ANY_ROLE)
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("post/{postId}")
    public List<Comment> getByPost(@PathVariable("postId")Integer postId)
    {
        return commentService.getByPost(postId);
    }

    @PostMapping
    public ResponseEntity<Comment> insert(@RequestBody CommentRequestDTO commentRequestDTO)
    {
        return commentService.insert(commentRequestDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Comment> delete(@PathVariable("id")Integer id)
    {
        return commentService.delete(id);
    }
}
