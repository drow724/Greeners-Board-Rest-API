package com.greeners.rest.api.service.board;

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
import com.greeners.rest.api.entity.board.Post;
import com.greeners.rest.api.model.ParamsPost;
import com.greeners.rest.api.repository.board.BoardRepository;
import com.greeners.rest.api.repository.board.PostRepository;
import com.greeners.rest.api.service.cache.CacheSevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    
    private final PostRepository postRepository;
    
    private final CacheSevice cacheSevice;

//    private final SympathyService SympathyService;
    
    public Board insertBoard(String boardName) {
        return boardRepository.save(Board.builder().name(boardName).build());
    }
    
    // 게시판 이름으로 게시판을 조회. 없을경우 CResourceNotExistException 처리
    @Cacheable(value = CacheKey.BOARD, key = "#boardName", unless = "#result == null")
    public Board findBoard(String boardName) {
        return Optional.ofNullable(boardRepository.findByName(boardName)).orElseThrow(CustomResourceNotExistException::new);
    }

    // 게시판 이름으로 게시글 리스트 조회.
    @Cacheable(value = CacheKey.POSTS, key = "#boardName", unless = "#result == null")
    public List<Post> findPosts(String boardName) {
        return postRepository.findByBoardOrderByPostIdDesc(findBoard(boardName));
    }

    // 게시글ID로 게시글 단건 조회. 없을경우 CResourceNotExistException 처리
    @Cacheable(value = CacheKey.POST, key = "#postId", unless = "#result == null")
    public Post getPost(long postId) {
        return postRepository.findById(postId).orElseThrow(CustomResourceNotExistException::new);
    }
    
    // 게시글을 등록합니다. 게시글의 회원UID가 조회되지 않으면 CUserNotFoundException 처리합니다.
    @CacheEvict(value = CacheKey.POSTS, key = "#boardName")
    @ForbiddenWordCheck
    public Post writePost(String boardName, ParamsPost paramsPost) {
        Board board = findBoard(boardName);
        Post post = new Post();
        post.setBoard(board);
        post.setAuthor(paramsPost.getAuthor());
        post.setTitle(paramsPost.getTitle());
        post.setContent(paramsPost.getContent());
        post.setSympathy_count(0L);
        post.setComment_count(0L);
        return postRepository.save(post);
    }

    @ForbiddenWordCheck
    public Post updatePost(long postId, String author, ParamsPost paramsPost) {
        Post post = getPost(postId);
        if (!author.equals(post.getAuthor()))
            throw new CustomNotOwnerException();

        // 영속성 컨텍스트의 변경감지(dirty checking) 기능에 의해 조회한 Post내용을 변경만 해도 Update쿼리가 실행됩니다.
        post.setTitle(paramsPost.getTitle());
        post.setContent(paramsPost.getContent());
        cacheSevice.deleteBoardCache(post.getPostId(), post.getBoard().getName());
        return post;
    }

    // 게시글을 삭제합니다. 게시글 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
    public boolean deletePost(long postId, String author) {
        Post post = getPost(postId);
        
        if (!author.equals(post.getAuthor()))
            throw new CustomNotOwnerException();
        
        postRepository.delete(post);
        cacheSevice.deleteBoardCache(post.getPostId(), post.getBoard().getName());
        return true;
    }
    
//    public boolean SympathyPost(long postId, String author) {
//        
//    	Sympathy sympathy = SympathyService.findSyampathy(postId);
//
//    	if(!author.equals(sympathy.getAuthor()))
//    		throw new CustomNotOwnerException();
//
//        postRepository.sympathyPost(postId);
//        return true;
//    }
}