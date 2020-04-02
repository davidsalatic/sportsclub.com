package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.PostRequestDTO;
import com.eryce.sportsclub.models.Post;
import com.eryce.sportsclub.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private AppUserService appUserService;

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public ResponseEntity<Post> insert(PostRequestDTO postRequestDTO) {
        postRepository.save(postRequestDTO.generatePost());
        mailService.sendNewPostMessageToAllUsers(appUserService.getEmails(appUserService.getAll()),postRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Post> delete(Integer id) {
        postRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
