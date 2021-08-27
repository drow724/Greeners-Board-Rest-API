package com.greeners.rest.api.repository.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greeners.rest.api.entity.board.Board;
import com.greeners.rest.api.entity.board.Post;

public interface PostJpaRepo extends JpaRepository<Post, Long> {
    List<Post> findByBoardOrderByPostIdDesc(Board board);
}