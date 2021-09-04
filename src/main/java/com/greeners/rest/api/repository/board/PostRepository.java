package com.greeners.rest.api.repository.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.greeners.rest.api.entity.board.Board;
import com.greeners.rest.api.entity.board.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByBoardOrderByPostIdDesc(Board board);
    
    Post findByPostId(Long postId);
    
    @Modifying
    @Query(value= "UPDATE Post p SET p.sympathy_count = p.sympathy_count + 1 WHERE p.postId = ?1")
    void sympathyPost(Long postId);
}
