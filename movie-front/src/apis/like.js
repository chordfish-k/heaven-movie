import { http } from '@/utils/http'

/**
 * 获取该电影是否已点赞
 * @param {*} movieId 
 * @returns 
 */
export const getMovieLikeAPI = (movieId) => {
    return http({
        url: '/user/like/'+movieId,
        method: 'GET',
    });
}

/**
 * 反转该电影的点赞
 * @param {*} movieId 
 * @returns 
 */
export const putMovieLikeAPI = (movieId) => {
    return http({
        url: '/user/like/'+movieId,
        method: 'PUT',
    });
}