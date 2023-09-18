<template>
    <div class="container">

        <div class="inner">
            <main-title-component></main-title-component>
            <div class="search">
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text" v-on:keyup.enter="clickSearch" v-model="this.query" placeholder="영화, 유저를 검색해보세요.">
            </div>
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
    methods: {
        clickSearch() {
            if(this.query.trim()!='') {
                this.$router.push("/search/"+this.query.trim());
                this.query = '';
            }
        }
    },
}
</script>

<style scoped>
.container {
    max-width: 1280px;
    display: flex;
    flex-direction: column;
    gap: 72px;
}
.search {
    border: 1px solid var(--G100);
    background: var(--G50);
    padding: 0px 20px;
    max-width: 400px;
    height: 60px;
    border-radius: 8px;
    margin: 24px auto 0;
    display: flex;
    gap: 12px;
    align-items: center;
}
.search input {
    font-size: 18px;
}
.search i {
    color: var(--G300);
    font-size: 14px;
}
</style>