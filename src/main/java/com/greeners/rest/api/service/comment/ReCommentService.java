package com.greeners.rest.api.service.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.greeners.rest.api.advice.exception.CustomNotOwnerException;
import com.greeners.rest.api.advice.exception.CustomResourceNotExistException;
import com.greeners.rest.api.common.CacheKey;
import com.greeners.rest.api.entity.board.Comment;
import com.greeners.rest.api.entity.board.ReComment;
import com.greeners.rest.api.model.ParamsReComment;
import com.greeners.rest.api.repository.comment.CommentRepository;
import com.greeners.rest.api.repository.comment.ReCommentRepository;
import com.greeners.rest.api.service.cache.CacheSevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReCommentService {

    private final ReCommentRepository reCommentRepository;
    
    private final CommentRepository commentRepository;
    
    private final CacheSevice cacheSevice;

    // 게시글ID로 게시글 단건 조회. 없을경우 CResourceNotExistException 처리
    @Cacheable(value = CacheKey.COMMENT, key = "#commentId", unless = "#result == null")
    public Comment findComment(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(CustomResourceNotExistException::new);
    }
    
    // 댓글ID로 댓글 단건 조회. 없을경우 CResourceNotExistException 처리
    @Cacheable(value = CacheKey.RECOMMENT, key = "#reCommentId", unless = "#result == null")
    public ReComment getReComment(long reCommentId) {
        return reCommentRepository.findById(reCommentId).orElseThrow(CustomResourceNotExistException::new);
    }
    
    // 게시글 번호로 댓글 리스트 조회.
    @Cacheable(value = CacheKey.RECOMMENTS, key = "#commentId", unless = "#result == null")
    public List<ReComment> findReComments(long commentId) {
        return reCommentRepository.findByCommentOrderByReCommentIdDesc(findComment(commentId));
    }

    // 댓글을 등록합니다.
    @CacheEvict(value = CacheKey.RECOMMENTS, key = "#commentId")
    public ReComment writeReComment(long commentId, ParamsReComment paramsReComment) {
    	Comment comment = findComment(commentId);
        ReComment reComment = new ReComment();
        reComment.setComment(comment);
        reComment.setAuthor(paramsReComment.getAuthor());
        reComment.setContent(paramsReComment.getContent());
        return reCommentRepository.save(reComment);
    }

    public ReComment updateReComment(long reCommentId, String author, ParamsReComment paramsReComment) {
    	ReComment reComment = getReComment(reCommentId);
        if (!author.equals(reComment.getAuthor()))
            throw new CustomNotOwnerException();

        // 영속성 컨텍스트의 변경감지(dirty checking) 기능에 의해 조회한 Comment내용을 변경만 해도 Update쿼리가 실행됩니다.
        reComment.setContent(paramsReComment.getContent());
        cacheSevice.deleteCommentCache(reComment.getReCommentId(), reComment.getComment().getCommentId());
        return reComment;
    }

    // 댓글을 삭제합니다. 댓글 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
    public boolean deleteReComment(long reCommentId, String author) {
    	ReComment reComment = getReComment(reCommentId);
        
        if (!author.equals(reComment.getAuthor()))
            throw new CustomNotOwnerException();
        
        reCommentRepository.delete(reComment);
        cacheSevice.deleteCommentCache(reComment.getReCommentId(), reComment.getComment().getCommentId());
        return true;
    }
    
}