package com.greeners.rest.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.greeners.rest.api.model.ParamsPost;
import com.greeners.rest.api.service.board.BoardService;

@SpringBootTest
public class BoardServiceTest {
	
	@Autowired
	private BoardService service;

	
	@Test
	public void testWrite() {
		
		ParamsPost paramsPost = new ParamsPost();
		
		paramsPost.setAuthor("TEST");
		paramsPost.setTitle("TEST");
		paramsPost.setContent("TEST");
		
		service.writePost("free", paramsPost);

	}
	
	@Test
	public void testFindBoard() {
		
		service.findBoard("free");

	}
	
	@Test
	public void testGetPost() {
		
		service.getPost(1L);

	}
	
	@Test
	public void testfindPosts() {
		
		service.findPosts("free");

	}
	
	@Test
	public void testUpdate() {
		
		ParamsPost paramsPost = new ParamsPost();
		
		paramsPost.setAuthor("TEST");
		paramsPost.setTitle("TEST TEST");
		paramsPost.setContent("TEST TEST");
		
		service.updatePost(1L, "TEST", paramsPost);

	}

	@Test
	public void testDelete() {
		
		service.deletePost(1L, "TEST");

	}
}
