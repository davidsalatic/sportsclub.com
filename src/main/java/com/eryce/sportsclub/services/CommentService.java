package com.eryce.sportsclub.services;

import com.eryce.sportsclub.dto.CommentRequestDTO;
import com.eryce.sportsclub.dto.PostRequestDTO;
import com.eryce.sportsclub.models.Comment;
import com.eryce.sportsclub.models.Post;
import com.eryce.sportsclub.repositories.CommentRepository;
import com.eryce.sportsclub.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MailService mailService;

    public List<Comment> getByPost(Integer postId) {
        Post post = postRepository.getOne(postId);
        List<Comment> comments = commentRepository.findAllByPost(post);
        Collections.reverse(comments);
        return comments;
    }

    public ResponseEntity<Comment> insert(CommentRequestDTO commentRequestDTO) {
        commentRepository.save(commentRequestDTO.generateComment());

        List<String> participants = getParticipantsInPostEmails(commentRequestDTO.getPost());

        mailService.sendNewCommentMessageToParticipants(participants,commentRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<String> getParticipantsInPostEmails(PostRequestDTO post) {
        List<String>emails = new ArrayList<>();
        emails.add(post.getAppUser().getUsername());//post author

        for(Comment comment:getByPost(post.getId()))
         //authors of comments in the post
            emails.add(comment.getAppUser().getUsername());
        return emails;
    }

    public ResponseEntity<Comment> delete(Integer id)
    {
        commentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
