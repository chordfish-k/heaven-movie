package com.chord.lsr.service;

public interface MovieLikeService {
    /**
     * 反转点赞
     * @param movieId
     */
    void likeReverse(Long movieId);

    /**
     * 查询是否已经点赞
     * @param movieId
     * @return
     */
    Integer isLike(Long movieId);
}
