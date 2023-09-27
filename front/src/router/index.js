import {createRouter, createWebHistory} from 'vue-router'
import store from '@/store/index'
import axios from '@/axios/index'
import routes from './routes'

const router = createRouter({
    history: createWebHistory(),
    // 라우터 선택 효과
    linkActiveClass: "route-active",
    linkExactActiveClass: "route-active",
    // 페이지 이동 시 스크롤 탑 적용
    scrollBehavior() {
        return { top: 0 }
    },
    routes
})

// 라우터 가드
router.beforeEach(function (to, from, next) {

    // 프로필 모달 닫기
    store.commit("modal/setProfile", false);

    // 페이지 위치 정보 저장
    if(to.path!='/auth/login' && !to.path.startsWith('/auth') && !to.path.endsWith("/error") && !to.path.startsWith("/admin")) {
        store.state.local.location = to.path;
    }
    // 인증 처리
    axios.get(`/route/${to.path.split("/")[1]}`).then(() => {
        next();
    });
});

export default router;