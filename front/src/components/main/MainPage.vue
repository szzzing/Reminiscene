<template>
    <div class="container">
        <div class="category">
            <router-link class="category-item" to="/list">영화 검색</router-link>
            <router-link class="category-item" to="/admin">관리자</router-link>
        </div>
        <div v-if="this.$store.state.auth.user!=null">{{ this.$store.state.auth.user.nickname }} 님</div>
        <router-view></router-view>
    </div>
</template>
 
<script>
import anonymous from '../../axios/anonymousAxios'

export default {
    methods: {
        do() {
            anonymous
            .get('/api.themoviedb.org/3/search/movie?query='+12345+'&api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR&page='+1)
            .then(function(response) {
                console.log(response);
            });
            console.log('search');
        }
    }
}
</script>

<style scoped>
    .category {
        display: flex;
        justify-content: center;
        gap: 20px;
    }
    .category-item {
        width: 120px;
        height: 40px;
        line-height: 40px;
        border-radius: 12px;
        background: var(--G50);
        color: var(--G1000);
        text-align: center;
        font-size: 16px;
    }
</style>