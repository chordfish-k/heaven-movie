import {http} from '@/utils/http'


/**
 * 根据id查询电影
 * @returns 
 */
export const getCategoryByTypeAPI = (id) => {
    return http({
        url: `user/category?type=${id}`,
        method: "GET",
    })
}