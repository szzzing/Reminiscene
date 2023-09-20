<template>
    <div id="search-suggest" v-if="this.list.length!=0">
        <div class="container">
            <router-link class="item" v-for="(movie) in this.list" :key="movie" :to="`/detail/${movie.id}`">
                <div class="title">{{ movie.title }}</div>
                <div class="release-date">{{ movie.releaseDate.substring(0, 4) }}</div>
            </router-link>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            list: [],
        }
    },
    props: [
        'query',
    ],
    watch: {
        query() {
            this.axios.get(`movie/search/${this.query}`)
            .then((response)=>{
                this.list = response.data;
            })
        },
    },
}
</script>

<style scoped>
#search-suggest {
    background: var(--BLUR);
    width: 100%;
    backdrop-filter: blur(16px);
    position: relative;
    top: 0;
    right: 0;
    left: 0;
}
.container {
    padding: 16px 0;
}
.item {
    display: flex;
    align-items: center;
    gap: 6px;
}
.title {
    font-weight: 600;
    color: var(--G800);
}
.release-date {
    color: var(--O600);
    font-size: 14px;
}
</style>