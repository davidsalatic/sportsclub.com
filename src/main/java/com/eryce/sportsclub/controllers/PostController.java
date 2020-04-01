package com.eryce.sportsclub.controllers;

import com.eryce.sportsclub.constants.Authorize;
import com.eryce.sportsclub.constants.Routes;
import com.eryce.sportsclub.dto.PostRequestDTO;
import com.eryce.sportsclub.models.Post;
import com.eryce.sportsclub.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routes.POST_BASE)
@PreAuthorize(Authorize.HAS_ANY_ROLE)
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAll()
    {
        return postService.getAll();
    }

    @PostMapping
    public ResponseEntity<Post> insert(@RequestBody PostRequestDTO postRequestDTO)
    {
        return postService.insert(postRequestDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Post> delete(@PathVariable("id")Integer id)
    {
        return postService.delete(id);
    }
}
