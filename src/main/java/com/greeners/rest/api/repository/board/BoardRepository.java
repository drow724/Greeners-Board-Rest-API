package com.greeners.rest.api.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greeners.rest.api.entity.board.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
    Board findByName(String name);
}