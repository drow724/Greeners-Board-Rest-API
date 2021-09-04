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
import com.greeners.rest.api.entity.board.ReComment;
import com.greeners.rest.api.model.ParamsComment;
import com.greeners.rest.api.model.ParamsReComment;
import com.greeners.rest.api.model.response.CommonResult;
import com.greeners.rest.api.model.response.ListResult;
import com.greeners.rest.api.model.response.SingleResult;
import com.greeners.rest.api.service.comment.ReCommentService;
import com.greeners.rest.api.service.common.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"ReComment"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/recomment")
public class ReCommentController {

    private final ReCommentService reCommentService;
    
    private final ResponseService responseService;
    
    @ApiOperation(value = "대댓글 목록", notes = "대댓글 목록을 조회")
    @GetMapping(value = "/{commentId}/list")
    public ListResult<ReComment> reComments(@PathVariable long commentId) {
        return responseService.getListResult(reCommentService.findReComments(commentId));
    }

    @ApiOperation(value = "대댓글 작성", notes = "대댓글 작성")
    @PostMapping(value = "/{commentId}/write")
    public SingleResult<ReComment> reComment(@PathVariable long commentId, @Valid @ModelAttribute ParamsReComment reComment) {
        return responseService.getSingleResult(reCommentService.writeReComment(commentId, reComment));
    }

    @ApiOperation(value = "대댓글 조회", notes = "대댓글 조회")
    @GetMapping(value = "/{recommentId}")
    public SingleResult<ReComment> comment(@PathVariable long reCommentId) {
        return responseService.getSingleResult(reCommentService.getReComment(reCommentId));
    }

    @ApiOperation(value = "대댓글 수정", notes = "대댓글 수정")
    @PutMapping(value = "/{recommentId}/{author}")
    public SingleResult<ReComment> comment(@PathVariable long reCommentId, @Valid @ModelAttribute ParamsReComment comment, @PathVariable String author) {
        return responseService.getSingleResult(reCommentService.updateReComment(reCommentId, author,  comment));
    }

    @ApiOperation(value = "대댓글 삭제", notes = "대댓글 삭제")
    @DeleteMapping(value = "/{recommentId}/{author}")
    public CommonResult deletePost(@PathVariable long reCommentId, @PathVariable String author) {
    	reCommentService.deleteReComment(reCommentId, author);
        return responseService.getSuccessResult();
    }
}