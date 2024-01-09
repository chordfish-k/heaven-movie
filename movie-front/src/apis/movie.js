import {http} from '@/utils/http'


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
    // console.log("getMovieQueryAPI",queryDTO)
    return http({
        url: 'user/movie',
        method: "GET",
        params: queryDTO
    })
}

/**
 * 增加观看次数
 * @param {*} id 
 * @returns 
 */
export const putMovieViewAPI = (id) => {
    // console.log("getMovieQueryAPI",queryDTO)
    return http({
        url: 'user/movie/'+id,
        method: "PUT",
    })
}
