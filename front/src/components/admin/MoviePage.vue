<template>
    <div id="user">
        <div class="inner">
            <div class="search-area">
                <div class="option">
                    <div class="name">아이디</div>
                    <input type="number" v-model="this.id" placeholder="40">
                </div>
                <div class="option">
                    <div class="name">제목</div>
                    <input type="text" v-model="this.query" placeholder="프로메테우스" maxlength="40">
                </div>
                <div class="option">
                    <div class="name">장르</div>
                    <transition-group name="list" tag="div" class="genre-list">
                        <div class="genre" v-for="(genre) in this.genreList" :key="genre" :class="{'checked' : genre.checked}">
                            <i class="fa-solid fa-square-check" @click="genre.checked = !genre.checked"></i>
                            <div>{{ genre.name }}</div>
                        </div>
                    </transition-group>
                </div>
                <div class="medium-button" @click="search">영화 검색</div>
            </div>

            <div class="list-area">
                <table class="movie-list">
                    <tr>
                        <th></th>
                        <th>제목</th>
                        <th>장르</th>
                        <th>러닝타임</th>
                        <th>개봉일</th>
                    </tr>
                    <tr v-for="movie in this.movieList" :key="movie">
                        <td>{{ movie.id }}</td>
                        <td>{{ movie.title }}</td>
                        <td>{{ movie.genre }}</td>
                        <td>{{ movie.runtime+"분" }}</td>
                        <td>{{ movie.releaseDate.replaceAll("-", ".") }}</td>
                    </tr>
                </table>
            </div>
        </div>
        <infinite-loading @infinite="getList"></infinite-loading>
    </div>
</template>

<script>
import InfiniteLoading from 'infinite-loading-vue3-ts';

export default {
    components: {
        InfiniteLoading,
    },
    data() {
        return {
            query: null,
            id: null,
            genreList: null,
            movieList: [],
            page: 1,
            state: null,
            genre: '',
        }
    },
    created() {
        this.axios.get("/genre")
        .then((response)=>{
            this.genreList = response.data;
        })
    },
    methods: {
        getList($state) {
            this.state = $state;
            const params = {
                page: this.page,
                query: this.query,
                id: this.id,
                genre: this.genre,
            }
            this.axios.get("/movie", {params})
            .then((response)=>{
                if(response.data.list.length>0) {
                    if(response.data.page==1) {
                        this.movieList = response.data.list;
                    } else {
                        this.movieList.push(...response.data.list);
                    }
                    this.page = response.data.page+1;
                    this.state.loaded();
                }
            })
        },
        search() {
            this.genre = '';
            for(let genre of this.genreList) {
                if(genre.checked) {
                    this.genre += genre.name+'/';
                }
            }
            this.movieList = [];
            this.page = 1;
            this.getList(this.state);
        },
    }
}
</script>

<style scoped>
.inner {
    display: flex;
    gap: 36px;
}
@media screen and (max-width:860px) {
    .inner {
        flex-direction: column;
        gap: 36px;
    }
    .search-area {
        display: flex;
        flex-wrap: wrap;
        padding-top: 0;
        width: 100%;
        gap: 24px;
    }
    input[type=text], input[type=number] {
        border-radius: 8px;
        font-size: 16px;
    }
}
.search-area {
    display: flex;
    flex-direction: column;
    gap: 24px;
    width: 260px;
    flex-shrink: 0;
    background: var(--G50);
    border-radius: 8px;
    padding: 20px;
    place-self: flex-start;
}
.option {
    display: flex;
    flex-direction: column;
    gap: 8px;
}
input[type=text], input[type=number] {
    padding: 4px 12px;
    border: 1px solid var(--G200);
    background: var(--G100);
    border-radius: 8px;
    font-size: 16px;
}
.name {
    color: var(--G600);
    font-weight: 600;
}
.genre-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
}
.genre {
    display: flex;
    align-items: center;
    gap: 8px;
    width: 50%;
}
.genre div {
    color: var(--G500);
}
.genre i {
    font-size: 18px;
    color: var(--G200);
    cursor: pointer;
}
.genre.checked i {
    color: var(--FOCUS);
}
.genre.checked div {
    color: var(--G1000);
}
.list-area {
    width: 100%;
}
.movie-list {
    width: 100%;
}
tr > th,
tr > td:first-child,
tr > td:nth-child(3),
tr > td:nth-child(4),
tr > td:nth-child(5) {
    text-align: center;
}
tr > td:first-child {
    color: var(--G600);
}
td {
    font-size: 14px;
    padding: 12px 4px;
}
th {
    color: var(--G600);
    padding: 12px 4px;
}
tr {
    border-bottom: 1px solid var(--G200);
}
table {
    border-collapse:collapse;
}
</style>