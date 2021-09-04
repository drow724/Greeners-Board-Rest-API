package com.greeners.rest.api.service.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.greeners.rest.api.common.CacheKey;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CacheSevice {

    @Caching(evict = {
            @CacheEvict(value = CacheKey.POST, key = "#postId"),
            @CacheEvict(value = CacheKey.POSTS, key = "#boardName"),
            @CacheEvict(value = CacheKey.COMMENT, key = "#commentId"),
            @CacheEvict(value = CacheKey.COMMENTS, key = "#postId"),
            @CacheEvict(value = CacheKey.RECOMMENT, key = "#reCommentId"),
            @CacheEvict(value = CacheKey.RECOMMENTS, key = "#commentId")
    })
    public boolean deleteBoardCache(long postId, String boardName) {
        log.debug("deleteBoardCache - postId {}, boardName {}", postId, boardName);
        return true;
    }
    
    public boolean deletePostCache(long commentId, long PostId) {
        log.debug("deleteCommentCache - commentId {}, PostId {}", commentId, PostId);
        return true;
    }
    
    public boolean deleteCommentCache(long reCommentId, long commentId) {
        log.debug("deleteReCommentCache - reCommentId {}, commentId {}", reCommentId, commentId);
        return true;
    }
}