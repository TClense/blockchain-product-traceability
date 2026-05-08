import axios from 'axios';
import { Message } from 'element-ui';
import { localStorageService } from './commonUtil';

// 1. 配置请求根路径
const instance = axios.create({
    baseURL: 'http://localhost:8010', // 替换为你的API根路径
    timeout: 5000, // 请求超时时间
});

// 2. 请求拦截器
instance.interceptors.request.use(
    (config) => {
        const userInfo = localStorageService.getItem('userInfo');
        if (userInfo && userInfo.address) {
            config.headers['address'] = userInfo.address
        }
        return config;
    },
    (error) => {
        return error
    }
);

// 3. 响应拦截器
instance.interceptors.response.use(
    (success) => {
        // 响应成功拦截器
        return success.data;
    },
    (error) => {
        // 鉴于正常服务器运行状况，都封装status200 | 处理之外，直接寄了，就不返回了
        console.log(error);
        Message.error(error)
        return {  code: 500, mes: '服务器错误'}
    }
);

// 导出封装后的axios实例
export default instance;

