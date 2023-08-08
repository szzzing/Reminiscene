<template>
    <div class="container">
        <div class="input-box">
            <input type="text" placeholder="엘리멘탈" v-model="query">
        </div>
        <div class="result" v-if="this.$store.state.movie.query!=''">
            <b>{{ this.$store.state.movie.query }}</b>에 대한 영화를 보여줄게.
            {{ this.word }}
        </div>
        
        <list-component v-if="this.$store.state.movie.query!=''">
        </list-component>
        
        <div class="next medium-button"
            v-if="this.$store.state.movie.page<this.$store.state.movie.maxPage"
            @click="this.nextPage()">
            다음 페이지
        </div>
    </div>
</template>

<script>
import ListComponent from "./ListComponent.vue";

export default {
    components: {
        ListComponent,
    },
    data() {
        return {
            query: '',
            word: ''
        }
    },
    methods: {
        nextPage() {
            const page = this.$store.state.movie.page+1;
            this.$store.commit("movie/setPage", page);
            this.$store.dispatch("movie/searchList");
        }
    },
    watch: {
        query() {
            this.$store.commit('movie/setQuery', this.query);
            this.$store.dispatch('movie/searchList');
        }
    },
    // /detail 페이지로 이동시 검색결과 유지
    // 그 외 페이지로 이동시 검색결과 유지 x
    beforeRouteLeave(to, from, next) {
        if(!to.path.startsWith('/detail')) {
            this.$store.commit('movie/setQuery', '');
            this.$store.dispatch('movie/searchList', {});
        }
        next();
    },
}
</script>

<style scoped>
    .container {
        max-width: 1280px;
    }
    .input-box {
        max-width: 400px;
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
    .next {
        text-align: center;
        margin: 40px auto 0;
        width: 108px;
    }
</style>