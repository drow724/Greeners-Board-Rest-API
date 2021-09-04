package com.greeners.rest.api.repository.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greeners.rest.api.entity.board.Board;
import com.greeners.rest.api.entity.board.Comment;
import com.greeners.rest.api.entity.board.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPostOrderByCommentIdDesc(Post post);
	
}