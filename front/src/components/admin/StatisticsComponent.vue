<template>
    <div class="inner">
        <main-title-component></main-title-component>
    </div>

    <div class="inner" v-if="this.monthBestList">
        <title-component>
            <template v-slot:emoji>🏆</template>
            <template v-slot:title>이번 달<br>가장 주목받은 작품</template>
        </title-component>
        <movie-list-component v-bind:list="monthBestList" v-bind:hide="true" v-bind:rank="true"></movie-list-component>
    </div>

    <div class="inner" v-if="this.monthBestList">
        <title-component>
            <template v-slot:emoji>🏅</template>
            <template v-slot:title>모두가 인정하는 명작</template>
        </title-component>
        <movie-list-component v-bind:list="bestRateList" v-bind:hide="true" v-bind:rank="true"></movie-list-component>
    </div>
</template>
 
<script>
import MainTitleComponent from '../item/MainTitleComponent.vue';
import MovieListComponent from '../item/MovieListComponent.vue';
import TitleComponent from '../item/TitleComponent.vue';

export default {
    components: {
        TitleComponent,
        MovieListComponent,
        MainTitleComponent,
    },
    data() {
        return {
            query: '',
            monthBestList: [],
            bestRateList: [],
        }
    },
    beforeCreate() {
        this.axios.get("/movie/rank")
        .then((response)=>{
            this.monthBestList = response.data.monthBestList;
            this.bestRateList = response.data.bestRateList;
        })
    },
}
</script>

<style scoped>
</style>