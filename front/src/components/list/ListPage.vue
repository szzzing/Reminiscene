<template>
    <div class="container">
        <div class="input-box">
            <input type="text" placeholder="엘리멘탈" v-model="query">
        </div>
        <div class="result" v-if="this.$store.state.query!=''">
            <b>{{ this.$store.state.query }}</b>에 대한 영화를 보여줄게.
        </div>
        <card-list></card-list>
    </div>
</template>

<script>
import CardList from "./CardList.vue";

export default {
    components: {
        CardList,
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
</style>