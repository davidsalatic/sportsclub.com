package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
