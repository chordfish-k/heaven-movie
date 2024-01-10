import {http, baseURL} from '@/utils/http'
import axios from 'axios'


/**
 * 根据id查询电影文件名
 * @returns 
 */
export const getMovieFileNameByIdAPI = (id, token) => {
    const url = `${baseURL}/user/video/d/${id}?token=${token}`
    console.log("url", url)
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

// export const getMovieFileNameByIdAPI = (id) => {
//     return http({
//         url: `user/video/getUrl/${id}`,
//         method: "GET",
//     })
// }