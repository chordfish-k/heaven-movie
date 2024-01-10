import { http } from '@/utils/http'

/**
 * 查询用户历史记录
 * @param historyQueryDTO
 * @returns 
 */
export const getUserHistoryAPI = (historyQueryDTO) => {
    return http({
        url: '/user/history',
        method: 'GET',
        params: historyQueryDTO
    });
}

