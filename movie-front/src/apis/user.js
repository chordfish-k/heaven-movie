import { http } from '@/utils/http'

/**
 * 用户登录
 * @param loginDTO
 * @returns 
 */
export const postUserLoginAPI = (loginDTO) => {
    console.log("postUserLoginAPI",loginDTO )
    return http({
        url: '/user/user/login',
        method: 'POST',
        data: loginDTO
    });
}

/**
 * 用户注册
 * @param loginDTO 
 * @returns 
 */
export const postUserRegisterAPI = (registerDTO) => {
    return http({
        url: '/user/user/register',
        method: 'POST',
        data: registerDTO
    });
}

/**
 * 升级到VIP用户
 * @returns 
 */
export const putUserUpgradeAPI = () => {
    return http({
        url: '/user/user/privilege/upgrade',
        method: 'PUT',
    });
}

/**
 * 退出VIP用户
 * @returns 
 */
export const putUserDowngradeAPI = () => {
    return http({
        url: '/user/user/privilege/downgrade',
        method: 'PUT',
    });
}
