const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: false, // 컴포넌트명 단일 명칭 에러

    // npm run server 개발 진행시 포트가 다르기 때문에 프록시 설정해줘야 함
    // target : 백엔드 port가 들어감
    // changeOrigin -> true로 해야 CORS 문제 해결할 수 있음
    devServer: {
        port: 1024,
        proxy: {
            '/': {
                target: 'http://localhost:1000',
                changeOrigin: true,
                ws: false, // 웹소켓 off
            }
        },
        historyApiFallback: true,
    },
    outputDir: "../api/src/main/resources/static",

})