package com.greeners.rest.api.repository.sympathy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greeners.rest.api.entity.board.Post;
import com.greeners.rest.api.entity.board.Sympathy;

public interface SympathyRepository extends JpaRepository<Sympathy, Long> {

    Sympathy findByPostId(Post post);
    
}