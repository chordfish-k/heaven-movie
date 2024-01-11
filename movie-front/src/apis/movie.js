import {http, baseURL} from '@/utils/http'
import axios from 'axios'

/**
 * 根据id查询电影
 * @returns 
 */
export const getMovieByIdAPI = (id) => {
    return http({
        url: `user/movie/${id}`,
        method: "GET",
    })
}

/**
 * 根据条件查询电影
 * @param queryDTO \{showType, name, type, releaseTime, staff, page, pageSize}
 * @returns 
 */
export const getMovieQueryAPI = (queryDTO) => {
    return http({
        url: 'user/movie',
        method: "GET",
        params: queryDTO
    })
}

/**
 * 增加播放量并添加历史记录
 * @param movieId 
 * @returns 
 */
export const putMovieViewAPI = (movieId) => {
    return http({
        url: `user/movie/view/${movieId}`,
        method: "PUT",
    })
}


/**
 * 根据id获取电影视频流
 * @returns 
 */
export const getMovieBlobByIdAPI = (id, token) => {
    const url = `${baseURL}/user/movie/get/${id}?token=${token}`
    return axios({
        url,
        method: "GET",
        responseType: "blob",
        headers: {
            "token": token,
            "Content-Type":"video/mp4"
        }
    })
}


/**
 * 获取该电影是否已点赞
 * @param {*} movieId 
 * @returns 
 */
export const getMovieLikeAPI = (movieId) => {
    return http({
        url: '/user/movie/like/' + movieId,
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
        url: '/user/movie/like/' + movieId,
        method: 'PUT',
    });
}



/**
 * 根据分类类型id查询分类列表
 * @returns 
 */
export const getCategoryByTypeAPI = (id) => {
    return http({
        url: `user/movie/category?type=${id}`,
        method: "GET",
    })
}