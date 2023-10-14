<template>
    <div id="backdrop" v-if="this.movie">
        <div class="backdrop" :style="{'background-image': `url('/upload/backdrop/${movie.backdropPath}')` }"></div>
        <div class="container">
            <img class="poster" :src="`/upload/poster/${movie.posterPath}`" @click="$router.push(`/content/movie/${movie.id}`)">
            <div class="poster-area"></div>
            <div class="info">
                <div class="title">{{ movie.title }}</div>
                <div class="original-title">{{ movie.originalTitle }}</div>
                <div class="genre-release" v-if="movie.genre+movie.release_date!=''">
                    {{ [movie.genre, movie.releaseDate.substring(0,4)].join(" ・ ") }}
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
    height: 30vh;
}
.dark .backdrop {
    background-color: var(--G50);
}
.container {
    max-width: 960px;
    padding: 20px 0;
    display: flex;
    gap: 20px;
    position: relative;
}
.poster {
    position: absolute;
    bottom: 20px;
    border-radius: 4px;
    border: 1px solid var(--O200);
    object-fit: cover;
    cursor: pointer;
}
.poster, .poster-area {
    width: 160px;
    flex-shrink: 0;
}
.flex-container {
    display: flex;
    flex-wrap: wrap-reverse;
    gap: 20px;
}
.title {
    font-size: 24px;
    font-weight: 700;
    line-height: 1.2;
    word-break: keep-all;
}
.genre-release {
    margin-top: 8px;
    color: var(--G500);
}
@media screen and (max-width:600px) {
    .backdrop {
        height: 20vh;
    }
    .poster {
        width: 120px;
    }
    .poster-area {
        width: 120px;
    }
    .title {
        font-size: 20px;
    }
    .genre-release {
        font-size: 14px;
    }
}
</style>