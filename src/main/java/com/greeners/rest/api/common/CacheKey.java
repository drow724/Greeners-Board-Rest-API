package com.greeners.rest.api.common;

public class CacheKey {

    public static final int DEFAULT_EXPIRE_SEC = 60; // 1 minutes
    public static final String USER = "user";
    public static final int USER_EXPIRE_SEC = 60 * 5; // 5 minutes
    public static final String BOARD = "board";
    public static final int BOARD_EXPIRE_SEC = 60 * 10; // 10 minutes
    public static final String POST = "post";
    public static final String POSTS = "posts";
    public static final int POST_EXPIRE_SEC = 60 * 5; // 5 minutes
    public static final String COMMENT = "comment";
    public static final String COMMENTS = "comments";
    public static final int COMMENT_EXPIRE_SEC = 60 * 5; // 5 minutes
    public static final String RECOMMENT = "reComment";
    public static final String RECOMMENTS = "reComments";
    public static final int RECOMMENT_EXPIRE_SEC = 60 * 5; // 5 minutes
}