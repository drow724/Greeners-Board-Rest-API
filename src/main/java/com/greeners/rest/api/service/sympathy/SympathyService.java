package com.greeners.rest.api.service.sympathy;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.greeners.rest.api.advice.exception.CustomResourceNotExistException;
import com.greeners.rest.api.entity.board.Post;
import com.greeners.rest.api.entity.board.Sympathy;
import com.greeners.rest.api.repository.board.PostRepository;
import com.greeners.rest.api.repository.sympathy.SympathyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SympathyService {

//    private final SympathyRepository sympathyRepository;
//    
//    private final PostRepository postRepository;
    
//    public Sympathy findSyampathy(Post post) {
//        return Optional.ofNullable(sympathyRepository.findByPostId(post.getPostId())).orElseThrow(CustomResourceNotExistException::new);
//    }

}