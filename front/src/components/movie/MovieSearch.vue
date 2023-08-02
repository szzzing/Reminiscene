<template>
    <div class="container">
        <app-title></app-title>
        <div class="input-box">
            <input type="text" placeholder="엘리멘탈" v-model="query">
        </div>
        <div class="result" v-if="this.$store.state.query!=''">
            <b>{{ this.$store.state.query }}</b>에 대한 영화를 보여줄게.
        </div>
        <movie-list></movie-list>
    </div>
</template>

<script>
import AppTitle from '../common/AppTitle.vue';
import MovieList from "./MovieList.vue";

export default {
    components: {
        AppTitle,
        MovieList,
    },
    data() {
        return {
            query: ''
        }
    },
    watch: {
        query() {
            this.$store.commit('setQuery', this.query);
            this.$store.dispatch('searchList');
        }
    }
}
</script>

<style scoped>
    .container {
        max-width: 1280px;
    }
    .input-box {
        background: var(--G50);
        max-width: 400px;
        height: 60px;
        line-height: 60px;
        border-radius: 16px;
        padding: 0 24px;
        margin: 0 auto;
    }
    input {
        font-size: 18px;
        text-align: center;
    }
    :focus {
        outline: none;
    }
    .result {
        font-size: 24px;
        text-align: center;
        margin: 40px 0 60px;
        color: var(--G1000);
    }
</style>