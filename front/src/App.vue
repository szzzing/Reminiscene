<template>
    <div class="wrap">
        <header-component></header-component>
        <alert-component v-bind:alert="this.$store.state.auth.loginAlert">
            <template v-slot:emoji>⚠️</template>
            <template v-slot:text>인증이 만료되어 로그아웃 되었어요</template>
        </alert-component>
        <div class="flex-wrap">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
import HeaderComponent from './components/common/HeaderComponent.vue'
import AlertComponent from './components/modal/AlertComponent.vue';

export default {
    components: {
        HeaderComponent,
        AlertComponent,
    },
    data() {
        return {
            movieList: {}
        }
    },
    methods: {
        getList(list) {
            this.movieList = list;
        }
    },
    // 테마 상태 반영
    created() {
        if(!this.$store.state.local.theme) {
            document.documentElement.classList.add('dark');
        }
    }
}
</script>

<style>

</style>