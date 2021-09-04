package com.greeners.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greeners.rest.api.entity.board.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

}