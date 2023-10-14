<template>
    <transition-group v-if="this.list && this.list.length!=0" v-bind:class="{'hide' : this.hide}" id="movie-list" tag="div" class="list">
        <router-link class="item" v-for="(movie, index) in this.list" :key="movie" :to="`/content/movie/${movie.id}`">
            <div class="poster" :style="{'background-image': `url(/upload/poster/${movie.posterPath}` }">
                <div v-if="this.rank" class="rank">{{ index==0 ? "🥇" : (index==1 ? "🥈" : (index==2 ? "🥉" : "")) }}</div>
            </div>
            <div class="info">
                <div class="title">{{ movie.title }}</div>
                <div class="genre">{{ movie.genre }}</div>
                <div class="footer">
                    <div class="releaseDate">{{ movie.releaseDate.split("-")[0] }}</div>
                    <div class="avg-rate" v-if="movie.avgRate>0">⭐️ {{ movie.avgRate.toFixed(1) }}</div>
                </div>
            </div>
        </router-link>
    </transition-group>
</template>

<script>
export default {
    props: [
        'list',
        'hide',
        'rank',
    ],
}
</script>

<style scoped>
#movie-list {
    display: flex;
    flex-wrap: wrap;
    gap: 40px 20px;
}
.hide#movie-list {
    width: 100%;
    overflow: scroll;
    flex-wrap: nowrap;
}
.hide .item {
    flex-shrink: 0;
    width: 14%;
}
.item {
    width: calc((100% - 120px)/7);
    flex-grow: 0;
    display: flex;
    flex-direction: column;
    gap: 8px;
}
@media screen and (max-width:1079px) {
    .item {
        width: calc((100% - 100px)/6);
    }
    .hide .item {
        width: 16%;
    }
}
@media screen and (max-width:960px) {
    .item {
        width: calc((100% - 80px)/5);
    }
    .hide .item {
        width: 20%;
    }
}
@media screen and (max-width:768px) {
    .item {
        width: calc((100% - 60px)/4);
    }
    .hide .item {
        width: 25%;
    }
}
@media screen and (max-width:525px) {
    .item {
        width: calc((100% - 40px)/3);
    }
    .hide .item {
        width: 35%;
    }
}
@media screen and (max-width: 360px) {
    .item {
        width: calc((100% - 20px)/2);
    }
    .hide .item {
        width: 40%;
    }
}
@media screen and (max-width: 270px) {
    .item {
        width: 100%;
    }
    .hide .item {
        width: 70%;
    }
}
.poster {
    width: 100%;
    padding-top: 150%;
    background-size: cover;
    background-position: center;
    display: flex;
    justify-content: center;
    border: 1px solid var(--G100);
    border-radius: 4px;
    position: relative;
}
.rank {
    position: absolute;
    font-size: 48px;
    top: 0px;
    left: 0px;
    line-height: 1;
}
.avg-rate {
    font-weight: 700;
    font-size: 14px;
}
.info {
    display: flex;
    flex-direction: column;
}
.title {
    font-weight: 600;
    line-height: 1.4;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.genre, .releaseDate {
    color: var(--G400);
    font-size: 14px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.footer {
    display: flex;
    justify-content: space-between;
}
</style>