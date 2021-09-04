package com.greeners.rest.api.service.comment;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.greeners.rest.api.advice.exception.CustomNotOwnerException;
import com.greeners.rest.api.advice.exception.CustomResourceNotExistException;
import com.greeners.rest.api.annotation.ForbiddenWordCheck;
import com.greeners.rest.api.common.CacheKey;
import com.greeners.rest.api.entity.board.Board;
import com.greeners.rest.api.entity.board.Comment;
import com.greeners.rest.api.entity.board.Post;
import com.greeners.rest.api.model.ParamsComment;
import com.greeners.rest.api.model.ParamsPost;
import com.greeners.rest.api.repository.board.PostRepository;
import com.greeners.rest.api.repository.comment.CommentRepository;
import com.greeners.rest.api.service.cache.CacheSevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    
    private final PostRepository postRepository;
    
    private final CacheSevice cacheSevice;

    // 게시글ID로 게시글 단건 조회. 없을경우 CResourceNotExistException 처리
    @Cacheable(value = CacheKey.POST, key = "#postId", unless = "#result == null")
    public Post findPost(long postId) {
        return postRepository.findById(postId).orElseThrow(CustomResourceNotExistException::new);
    }
    
    // 댓글ID로 댓글 단건 조회. 없을경우 CResourceNotExistException 처리
    @Cacheable(value = CacheKey.COMMENT, key = "#commentId", unless = "#result == null")
    public Comment getComment(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(CustomResourceNotExistException::new);
    }
    
    // 게시글 번호로 댓글 리스트 조회.
    @Cacheable(value = CacheKey.COMMENTS, key = "#postId", unless = "#result == null")
    public List<Comment> findComments(long postId) {
        return commentRepository.findByPostOrderByCommentIdDesc(findPost(postId));
    }

    // 댓글을 등록합니다.
    @CacheEvict(value = CacheKey.COMMENTS, key = "#postId")
    public Comment writeComment(long postId, ParamsComment paramsComment) {
        Post post = findPost(postId);
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(paramsComment.getAuthor());
        comment.setContent(paramsComment.getContent());
        return commentRepository.save(comment);
    }

    public Comment updateComment(long CommentId, String author, ParamsComment paramsComment) {
    	Comment comment = getComment(CommentId);
        if (!author.equals(comment.getAuthor()))
            throw new CustomNotOwnerException();

        // 영속성 컨텍스트의 변경감지(dirty checking) 기능에 의해 조회한 Comment내용을 변경만 해도 Update쿼리가 실행됩니다.
        comment.setContent(paramsComment.getContent());
        cacheSevice.deletePostCache(comment.getCommentId(), comment.getPost().getPostId());
        return comment;
    }

    // 댓글을 삭제합니다. 댓글 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
    public boolean deleteComment(long commentId, String author) {
    	Comment comment = getComment(commentId);
        
        if (!author.equals(comment.getAuthor()))
            throw new CustomNotOwnerException();
        
        commentRepository.delete(comment);
        cacheSevice.deletePostCache(comment.getCommentId(), comment.getPost().getPostId());
        return true;
    }
    
}