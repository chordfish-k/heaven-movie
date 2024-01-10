import {http} from '@/utils/http'


/**
 * 根据id查询电影文件名
 * @returns 
 */
export const getMovieFileNameByIdAPI = (id) => {
    return http({
        url: `user/video/getUrl/${id}`,
        method: "GET",
    })
}