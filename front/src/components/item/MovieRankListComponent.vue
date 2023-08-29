<template>
    <transition-group id="movie-list" tag="div" class="list">
        <router-link class="item" v-for="(movie, index) in this.list" :key="movie" :to="`/detail/${movie.id}`">
            <div class="poster" :style="{'background-image': `url(/upload/poster/${movie.posterPath}` }">
                <div class="rank">{{ index==0 ? "🥇" : (index==1 ? "🥈" : (index==2 ? "🥉" : "")) }}</div>
            </div>
            <div class="info">
                <div class="title">{{ movie.title }}</div>
                <div class="genre">{{ movie.genre }}</div>
                <!-- <div class="footer">
                    <div class="releaseDate">{{ movie.releaseDate.split("-")[0] }}</div>
                    <div class="avg-rate" v-if="movie.avgRate>0">⭐️ {{ movie.avgRate.toFixed(1) }}</div>
                </div> -->
            </div>
        </router-link>
    </transition-group>
</template>

<script>
export default {
    props: [
        'list',
    ],
}
</script>

<style scoped>
#movie-list {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
}
.item {
    width: calc((100% - 80px)/5);
    padding-bottom: 20px;
    flex-grow: 0;
    display: flex;
    flex-direction: column;
    gap: 8px;
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
}
.footer {
    display: flex;
    justify-content: space-between;
}
@media screen and (max-width:1179px) {
    .item {
        width: calc((100% - 60px)/4);
    }
    .item:nth-child(4) ~ .item {
        display: none;
    }
}
@media screen and (max-width:860px) {
    .item {
        width: calc((100% - 40px)/3);
    }
    .item:nth-child(3) ~ .item {
        display: none;
    }
}
@media screen and (max-width:600px) {
    .item {
        width: calc((100% - 20px)/2);
    }
    .item:nth-child(2) ~ .item {
        display: none;
    }
}
@media screen and (max-width:414px) {
    .item {
        width: 100%;
    }
    .item:nth-child(1) ~ .item {
        display: none;
    }
}
</style>