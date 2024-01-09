import {http} from '@/utils/http'

/**
 * 查询排行榜
 * @param {*} rankId 
 * @param {*} count 
 * @returns 
 */
export const getRankAPI = (rankId, count) => {
    return http({
        url: `user/rank?rankId=${rankId}&count=${count}`,
        method: "GET"
    })
}