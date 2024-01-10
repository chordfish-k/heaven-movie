import {http} from '@/utils/http'

/**
 * 查询排行榜
 * @param {*} rankId 
 * @returns 
 */
export const getRankAPI = (rankId) => {
    return http({
        url: `user/rank?rankId=${rankId}`,
        method: "GET"
    })
}