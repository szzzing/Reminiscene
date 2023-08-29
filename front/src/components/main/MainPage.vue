<template>
    <div class="container">

        <div class="inner" v-if="this.monthBestList">
            <title-component>
                <template v-slot:emoji>🧙‍♀️</template>
                <template v-slot:title>이번 달<br>가장 관심받은 작품</template>
            </title-component>
            <movie-rank-list-component v-bind:list="monthBestList"></movie-rank-list-component>
        </div>

        <div class="inner" v-if="this.monthBestList">
            <title-component>
                <template v-slot:emoji>🌟</template>
                <template v-slot:title>많은 별점을 받은 작품</template>
            </title-component>
            <movie-rank-list-component v-bind:list="bestRateList"></movie-rank-list-component>
        </div>

    </div>
</template>
 
<script>
import MovieRankListComponent from '../item/MovieRankListComponent.vue';
import TitleComponent from '../item/TitleComponent.vue';

export default {
    components: {
        TitleComponent,
        MovieRankListComponent,
    },
    data() {
        return {
            monthBestList: [],
            bestRateList: [],
        }
    },
    beforeCreate() {
        this.axios.get("/movies/rank")
        .then((response)=>{
            this.monthBestList = response.data.monthBestList;
            this.bestRateList = response.data.bestRateList;
        })
    }
}
</script>

<style scoped>
.container {
    max-width: 1280px;
    display: flex;
    flex-direction: column;
    gap: 72px;
}
</style>