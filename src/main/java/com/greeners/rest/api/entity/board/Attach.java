package com.greeners.rest.api.entity.board;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greeners.rest.api.entity.common.CommonDateEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attach extends CommonDateEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String uuid;
    @Column(nullable = false, length = 200)
    private String uploadPath;
    @Column(nullable = false, length = 100)
    private String fileName;
    @Column(nullable = false, length = 1)
    private Character fileType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post; // 게시글 - 게시판의 관계 - N:1

    // Join 테이블이 Json결과에 표시되지 않도록 처리.
    @JsonIgnore
    public Post getPost() {
        return post;
    }
    
}