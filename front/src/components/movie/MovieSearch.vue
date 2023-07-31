<template>
    <div class="container">
        <div class="title-area">
            <div class="title">
                🌙
            </div>
            <div class="sub-title">어떤 영화가 궁금해?</div>
        </div>
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
import MovieList from "./MovieList.vue";

export default {
  components: {
    MovieList
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
    div {
        text-align: center;
    }
    .title {
        margin: 0 0 12px;
        font-size: 40px;
        font-weight: 900;
        text-align: center;
        color: #fff;
    }
    .sub-title {
        color: #aaa;
        text-align: center;
        font-size: 18px;
        font-weight: 700;
    }
    input[type=text] {
        border: none;
        width: 100%;
        background: #f7f7f7;
        font-size: 20px;
        text-align: center;
    }
    input::placeholder {
        color: #ddd;
    }
    .input-box {
        background: #f9f9f9;
        max-width: 400px;
        height: 60px;
        line-height: 60px;
        border-radius: 16px;
        padding: 0 20px;
        margin: 20px auto 40px;
    }
    :focus {
        outline: none;
    }
    .result {
        font-size: 24px;
        margin: 40px 0 60px;
        color: #fff;
    }
</style>