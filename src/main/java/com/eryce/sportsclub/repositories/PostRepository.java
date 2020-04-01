package com.eryce.sportsclub.repositories;

import com.eryce.sportsclub.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer> {
}
