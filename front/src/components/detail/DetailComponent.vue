<template>
    <div id="detail" class="flex-container" v-if="this.movie">
        
        <div class="inner" v-if="movie.posterPath">
            <img class="poster" :src="movie.posterPath">
        </div>

        <div class="inner">
            <rate-component v-bind:movie="movie"></rate-component>
            <div class="info-area max-size" v-if="movie.overview!=''">
                <div class="tagline" v-if="movie.tagline!=''">{{ movie.tagline }}</div>
                <div class="overview">{{ movie.overview }}</div>
            </div>
        </div>
    </div>
</template>

<script>
import RateComponent from './RateComponent.vue';

export default {
    components: {
        RateComponent,
    },
    props: [
        'movie',
    ]
}
</script>

<style scoped>
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
.flex-container > .inner:first-child {
    flex-basis: 300px;
}
.flex-container > .inner:last-child {
    flex-basis: 600px;
    flex-grow: 1;
    flex-shrink: 1;
}
.title {
    font-size: 36px;
    font-weight: 700;
}
.genre-release {
    margin-top: 8px;
}
.genre-release, .runtime {
    font-weight: 500;
}
.poster {
    max-width: 300px;
    object-fit: cover;
    margin: auto;
}
.tagline {
    font-weight: 600;
    font-size: 18px;
    margin-bottom: 12px;
}
</style>