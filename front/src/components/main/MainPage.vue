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
            <div class="title">{{ new Date().getMonth()+1+"월의 영화" }}</div>
            <div class="sub-title">이번 달 가장 많은 관심을 받은 작품</div>
            <movie-list-component v-bind:list="monthBestList" v-bind:hide="true" v-bind:rank="true"></movie-list-component>
        </div>

        <div class="inner" v-if="this.monthBestList">
            <div class="title">클래식은 영원하다</div>
            <div class="sub-title">가장 높은 별점을 받은 작품</div>
            <movie-list-component v-bind:list="bestRateList" v-bind:hide="true" v-bind:rank="true"></movie-list-component>
        </div>

        <div class="inner" v-if="this.monthBestList">
            <div class="title">최근 개봉한 영화</div>
            <div class="sub-title">새로운 작품이 기다리고 있어요</div>
            <movie-list-component v-bind:list="newList" v-bind:hide="true"></movie-list-component>
        </div>

    </div>
</template>
 
<script>
import MainTitleComponent from '../item/MainTitleComponent.vue';
import MovieListComponent from '../item/MovieListComponent.vue';

export default {
    components: {
        MovieListComponent,
        MainTitleComponent,
    },
    data() {
        return {
            query: '',
            monthBestList: [],
            bestRateList: [],
            newList: [],
        }
    },
    beforeCreate() {
        this.axios.get("/movie/rank")
        .then((response)=>{
            this.monthBestList = response.data.monthBestList;
            this.bestRateList = response.data.bestRateList;
            this.newList = response.data.newList;
        })
    },
    methods: {
        clickSearch() {
            const query = this.query.trim().replace(/[^0-9a-zA-Zㄱ-ㅎ가-힣]/g, "");
            if(query!='') {
                this.$router.push("/search/"+query);
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
.title {
    font-size: 24px;
    font-weight: 700;
}
.sub-title {
    margin-bottom: 16px;
    font-weight: 500;
    color: var(--G500);
}
</style>