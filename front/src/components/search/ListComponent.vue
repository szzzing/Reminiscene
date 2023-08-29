<template>
    <transition-group id="movie-list" tag="div" class="list">
        <router-link class="item item-border" v-for="(movie) in this.list" :key="movie" :to="`/detail/${movie.id}`">
            <div class="thum" v-if="movie.poster_path"
            :style="{'background-image': 'url(https://image.tmdb.org/t/p/original/'+movie.poster_path+')' }">
                <div class="vote-average">
                    ⭐️
                    {{ movie.vote_average.toFixed(1) }}
                </div>
            </div>
            <div class="no-image" v-if="!movie.poster_path">
                <div class="icon">🌙</div>
            </div>
            <div class="info">
                <div class="title">
                    {{ movie.title }}
                </div>
                <div class="date">
                    {{ movie.release_date.split('-')[0] }}
                </div>
            </div>
        </router-link>
    </transition-group>
</template>

<script>

export default {
    props: [
        'list',
    ]
}
</script>

<style scoped>
.list {
    display: flex;
    gap: 60px 20px;
    flex-wrap: wrap;
    align-items: flex-start;
}
.item {
    width: calc((100% - 80px)/5);
    word-break: keep-all !important;
    cursor: pointer;
    border-radius: 8px;;
    overflow: hidden;
    flex-grow: 0;
}
@media screen and (max-width:1179px) {
    .item {
        width: calc((100% - 60px)/4);
    }
}
@media screen and (max-width:768px) {
    .item {
        width: calc((100% - 40px)/3);
    }
}
@media screen and (max-width:600px) {
    .item {
        width: calc((100% - 20px)/2);
    }
}
.info {
    text-align: center;
    padding: 12px 20px;
}
.thum, .no-image {
    position: relative;
    width: 100%;
    padding-top: 150%;
    background-size: cover;
    background-position: center;
    display: flex;
    justify-content: center;
}
.no-image {
    background-color: var(--G1000);
}
.no-image .icon {
    font-size: 36px;
    position: absolute;
    top: calc((100% - 50px)/2);
}
.info .title {
    font-weight: 600;
    text-align: center;
    word-break: keep-all !important;
}
.info .date {
    color: var(--G400);
    font-size: 14px;
    margin-bottom: 4px;
}
.vote-average {
    font-weight: 700;
    font-size: 14px;
    position: absolute;
    top: 12px;
    right: 12px;
    height: 32px;
    line-height: 32px;
    padding: 0px 12px 0px 10px;
    border-radius: 8px;;
    background: #f9fafb50;
    box-shadow: 0px 0px 10px var(--O100);
    backdrop-filter: blur(16px);
}
.dark .vote-average {
    background: #18181820;
}

</style>