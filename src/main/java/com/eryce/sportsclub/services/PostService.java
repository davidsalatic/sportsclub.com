package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.PostRequestDTO;
import com.eryce.sportsclub.models.Comment;
import com.eryce.sportsclub.models.Post;
import com.eryce.sportsclub.repositories.CommentRepository;
import com.eryce.sportsclub.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private CommentRepository commentRepository;

    public List<Post> getAll() {
        List<Post> posts=postRepository.findAll();
        Collections.reverse(posts); //we want the last comment to be on top
        return posts;
    }

    public Post getById(Integer id) {
        return postRepository.getOne(id);
    }

    public ResponseEntity<Post> insert(PostRequestDTO postRequestDTO) {
        postRepository.save(postRequestDTO.generatePost());
        mailService.sendNewPostMessageToAllUsers(appUserService.getEmails(appUserService.getAll()),postRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Post> delete(Integer id) {
        deleteCommentsOnPost(id);
        postRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void deleteCommentsOnPost(Integer postId)
    {
        for(Comment comment : commentRepository.findAllByPost(getById(postId)))
            commentRepository.delete(comment);
    }
}
