package com.greeners.rest.api.repository.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greeners.rest.api.entity.board.Comment;
import com.greeners.rest.api.entity.board.ReComment;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {

	List<ReComment> findByCommentOrderByReCommentIdDesc(Comment comment);
	
}