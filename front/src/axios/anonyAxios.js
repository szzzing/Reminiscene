import axios from 'axios'
import { router } from '../router/index'
import { store } from '../store/index'

const anonyAxios = axios.create({
    baseURL: 'http://localhost:8080/',
});

export default anonyAxios;