<template>
    <div id="backdrop" v-if="this.movie">
        <div class="backdrop" :style="{'background-image': 'url('+movie.backdropPath+')' }">
            <div class="container">
                <div class="title" v-html="movie.title"></div>
                <div class="original-title" v-if="movie.title !=movie.originalTitle" v-html="movie.originalTitle"></div>
                <div class="genre-release" v-if="movie.genre+movie.release_date!=''">
                    {{ movie.genre!='' && movie.releaseDate!='' ? [movie.genre, movie.releaseDate].join(" ・ ") : movie.genre + movie.releaseDate }}
                </div>
                <div class="runtime" v-if="movie.runtime!=0">
                    {{ movie.runtime+'분' }}
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    props: [
        'movie',
    ],
}
</script>

<style scoped>
.backdrop {
    background: var(--G1000);
    background-size: cover;
    background-position: center;
    height: 70vh;
    position: relative;
}
.dark .backdrop {
    background-color: var(--G50);
}
.backdrop * {
    color: #fff;
}
.backdrop > .container {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
}
.container {
    max-width: 1280px;
    padding: 40px 0;
}
.flex-container {
    display: flex;
    flex-wrap: wrap-reverse;
    gap: 20px;
}
.inner {
    display: flex;
    flex-direction: column;
    gap: 48px;
    justify-content: space-between;
}
.title {
    font-size: 30px;
    font-weight: 700;
    line-height: 1.2;
    word-break: keep-all;
}
.genre-release {
    margin-top: 8px;
}
.genre-release, .runtime {
    font-weight: 500;
}
@media screen and (max-width:960px) {
    .backdrop {
        height: 60vh;
    }
}
@media screen and (max-width:474px) {
    .backdrop {
        height: 50vh;
    }
    .title {
        font-size: 24px;
    }
}
</style>