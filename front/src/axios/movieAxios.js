import axios from 'axios'

const movieAxios = axios.create({
    baseURL: 'https://',
});

export default movieAxios;