package com.greeners.rest.api.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greeners.rest.api.entity.board.Board;
import com.greeners.rest.api.entity.board.Post;
import com.greeners.rest.api.model.ParamsPost;
import com.greeners.rest.api.model.response.CommonResult;
import com.greeners.rest.api.model.response.ListResult;
import com.greeners.rest.api.model.response.SingleResult;
import com.greeners.rest.api.service.board.BoardService;
import com.greeners.rest.api.service.common.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"Board"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/board")
public class BoardController {

    private final BoardService boardService;
    
    private final ResponseService responseService;

    @ApiOperation(value = "게시판 생성", notes = "신규 게시판을 생성한다.")
    @PostMapping(value = "/{boardName}")
    public SingleResult<Board> createBoard(@PathVariable String boardName) {
        return responseService.getSingleResult(boardService.insertBoard(boardName));
    }
    
    @ApiOperation(value = "글 목록", notes = "게시글 목록을 조회")
    @GetMapping(value = "/{boardName}/list")
    public ListResult<Post> posts(@PathVariable String boardName) {
        return responseService.getListResult(boardService.findPosts(boardName));
    }

    @ApiOperation(value = "글 작성", notes = "게시글을 작성")
    @PostMapping(value = "/{boardName}/write")
    public SingleResult<Post> post(@PathVariable String boardName, @Valid @ModelAttribute ParamsPost post) {

        return responseService.getSingleResult(boardService.writePost(boardName, post));
    }

    @ApiOperation(value = "게시글 조회", notes = "게시글을 조회")
    @GetMapping(value = "/{postId}")
    public SingleResult<Post> post(@PathVariable long postId) {
        return responseService.getSingleResult(boardService.getPost(postId));
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정")
    @PutMapping(value = "/{postId}/{author}")
    public SingleResult<Post> post(@PathVariable long postId, @Valid @ModelAttribute ParamsPost post, @PathVariable String author) {
        return responseService.getSingleResult(boardService.updatePost(postId, author,  post));
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제")
    @DeleteMapping(value = "/{postId}/{author}")
    public CommonResult deletePost(@PathVariable long postId, @PathVariable String author) {
        boardService.deletePost(postId, author);
        return responseService.getSuccessResult();
    }
    
//    @ApiOperation(value = "게시글 공감", notes = "게시글을 공감")
//    @PostMapping(value = "sympathy/{postId}/")
//    public CommonResult sympathyPost(@PathVariable long postId, @PathVariable String author) {
//        boardService.SympathyPost(postId, author);
//        return responseService.getSuccessResult();
//    }
}