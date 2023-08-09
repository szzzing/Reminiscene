<template>
    <div class="wrap">
        <header-component></header-component>
        <div class="flex-wrap">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
import HeaderComponent from './components/common/HeaderComponent.vue'

export default {
    components: {
        HeaderComponent,
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