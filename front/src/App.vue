<template>
        <header-component></header-component>
        <alert-component></alert-component>
        <div class="wrap">
            <router-view></router-view>
            <footer-component></footer-component>
        </div>
</template>

<script>
import HeaderComponent from './components/common/HeaderComponent.vue'
import AlertComponent from './components/modal/AlertComponent.vue';
import FooterComponent from './components/common/FooterComponent.vue';

export default {
    components: {
        HeaderComponent,
        AlertComponent,
        FooterComponent,
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