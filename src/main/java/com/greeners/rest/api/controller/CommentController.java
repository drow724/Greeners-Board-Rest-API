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

import com.greeners.rest.api.entity.board.Comment;
import com.greeners.rest.api.entity.board.Post;
import com.greeners.rest.api.model.ParamsComment;
import com.greeners.rest.api.model.ParamsPost;
import com.greeners.rest.api.model.response.CommonResult;
import com.greeners.rest.api.model.response.ListResult;
import com.greeners.rest.api.model.response.SingleResult;
import com.greeners.rest.api.service.comment.CommentService;
import com.greeners.rest.api.service.common.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"Comment"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    private final CommentService commentService;
    
    private final ResponseService responseService;
    
    @ApiOperation(value = "댓글 목록", notes = "댓글 목록을 조회")
    @GetMapping(value = "/{postId}/list")
    public ListResult<Comment> comments(@PathVariable long postId) {
        return responseService.getListResult(commentService.findComments(postId));
    }

    @ApiOperation(value = "댓글 작성", notes = "댓글 작성")
    @PostMapping(value = "/{postId}/write")
    public SingleResult<Comment> comment(@PathVariable long postId, @Valid @ModelAttribute ParamsComment comment) {
        return responseService.getSingleResult(commentService.writeComment(postId, comment));
    }

    @ApiOperation(value = "댓글 조회", notes = "댓글 조회")
    @GetMapping(value = "/{commentId}")
    public SingleResult<Comment> comment(@PathVariable long commentId) {
        return responseService.getSingleResult(commentService.getComment(commentId));
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글 수정")
    @PutMapping(value = "/{commentId}/{author}")
    public SingleResult<Comment> comment(@PathVariable long commentId, @Valid @ModelAttribute ParamsComment comment, @PathVariable String author) {
        return responseService.getSingleResult(commentService.updateComment(commentId, author,  comment));
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글 삭제")
    @DeleteMapping(value = "/{commentId}/{author}")
    public CommonResult deletePost(@PathVariable long commentId, @PathVariable String author) {
    	commentService.deleteComment(commentId, author);
        return responseService.getSuccessResult();
    }
}