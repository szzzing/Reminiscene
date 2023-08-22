<template>
    <detail-component></detail-component>
    <comment-component></comment-component>
</template>

<script>
import DetailComponent from './DetailComponent.vue'
import CommentComponent from './CommentComponent.vue'

export default {
    components: { 
        DetailComponent,
        CommentComponent,
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

</style>