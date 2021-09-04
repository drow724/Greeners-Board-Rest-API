package com.greeners.rest.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import com.greeners.rest.api.model.ParamsComment;
import com.greeners.rest.api.service.comment.CommentService;

@SpringBootTest
public class CommentServiceTest {

	@Autowired
	private CommentService service;

	@BeforeTestClass
	public void testWrite() {
		
		ParamsComment paramsComment = new ParamsComment();
		
        paramsComment.setAuthor("TEST");
        paramsComment.setContent("TEST");
		
		service.writeComment(1L, paramsComment);

	}
	
	@Test
	public void testFindPost() {
		
		service.findPost(1L);

	}
	
	@Test
	public void testGetComment() {
		
		service.getComment(1L);

	}
	
	@Test
	public void testfindComments() {
		
		service.findComments(1L);

	}
	

	
	@Test
	public void testUpdate() {
		
		ParamsComment paramsComment = new ParamsComment();
		
        paramsComment.setAuthor("TEST");
        paramsComment.setContent("TEST");
		
		service.writeComment(1L, paramsComment);

	}

	@AfterTestClass
	public void testDelete() {
		
		ParamsComment paramsComment = new ParamsComment();
		
        paramsComment.setAuthor("TEST");
        paramsComment.setContent("TEST");
		
		service.writeComment(1L, paramsComment);

	}

}