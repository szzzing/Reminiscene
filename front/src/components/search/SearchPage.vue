<template>
    <div class="container">
        <div class="query">
            <b>{{ this.query }}</b>의 검색결과
        </div>
        <div class="category">
            <router-link :to="`/search/${this.query}`">영화</router-link>
            <router-link :to="`/search/user/${this.query}`">유저</router-link>
            <div class="line"></div>
        </div>
        <router-view v-bind:query="query" :key="$route.params.query"></router-view>
    </div>
</template>

<script>
export default {
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
    gap: 48px;
}
.query {
    font-size: 18px;
}
.category {
    display: flex;
}
.category > * {
    border-bottom: 1px solid var(--G200);
    color: var(--G400);
    padding: 8px 16px;
}
.category a.route-active {
    border-bottom: 2px solid var(--FOCUS);
    color: var(--G1000);
}
.line {
    flex-grow: 1;
}
</style>