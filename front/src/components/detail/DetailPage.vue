<template>
    <div class="container">
        <detail-component></detail-component>
        <review-component></review-component>
    </div>
</template>

<script>
import DetailComponent from './DetailComponent.vue'
import ReviewComponent from './ReviewComponent.vue'

export default {
    components: { 
        DetailComponent,
        ReviewComponent,
    },
    // /list 페이지로 이동시 검색결과 유지
    // 그 외 페이지로 이동시 검색결과 유지 x
    beforeRouteLeave(to, from, next) {
        if(!to.path.startsWith('/list') && !to.path.startsWith('/login')) {
            this.$store.commit('movie/setQuery', '');
            this.$store.dispatch('movie/searchList', {});
        }
        next();
    },
}
</script>

<style scoped>
.wrap>.container:first-child {
    padding: 0;
    width: 100%;
}
</style>