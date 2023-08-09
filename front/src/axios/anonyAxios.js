import axios from 'axios'
import router from '../router/index'
import store from '../store/index'

const anonyAxios = axios.create({
    baseURL: 'http://localhost:8080/',
});

// 토큰이 만료되었을 시 전역 상태에서 삭제
apiAxios.interceptors.response.use(
    config => {
        return config
    },
    error => {
        console.log("anonyAxios Error");
    }
)

export default anonyAxios;