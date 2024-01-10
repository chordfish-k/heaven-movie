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
 * 根据id查询电影文件名
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