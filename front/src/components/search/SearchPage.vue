<template>
    <div class="container">
        <title-component>
            <template v-slot:emoji>🔎</template>
            <template v-slot:title>
                {{ this.query }}의<br>검색 결과
            </template>
        </title-component>
        <div class="category">
            <router-link :to="`/search/${this.query}`">영화</router-link>
            <router-link :to="`/search/user/${this.query}`">유저</router-link>
            <div class="line"></div>
        </div>
        <router-view v-bind:query="query" :key="$route.params.query"></router-view>
    </div>
</template>

<script>
import TitleComponent from '../item/TitleComponent.vue';
export default {
    components: {
        TitleComponent
    },
    updated() {
        this.query = this.$route.params.query;
    },
    data() {
        return {
            query: this.$route.params.query,
        }
    },
}
</script>

<style scoped>
.container {
    max-width: 1280px;
    display: flex;
    flex-direction: column;
}
.query {
    font-size: 18px;
}
.category {
    display: flex;
    margin-bottom: 36px;
}
.category > * {
    border-bottom: 1px solid var(--G200);
    color: var(--G400);
    padding: 8px 20px;
}
.category a.route-active {
    border-bottom: 2px solid var(--FOCUS);
    color: var(--G1000);
}
.line {
    flex-grow: 1;
}
</style>