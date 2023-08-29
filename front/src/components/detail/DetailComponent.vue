<template>
    <div id="detail" v-if="this.movie">
        
        <div class="movie-area">
            <div class="poster" v-if="movie.posterPath">
                <img :src="movie.posterPath">
            </div>
            
            <div class="info">
                <rate-component v-bind:movie="movie"></rate-component>
                <div class="info-area max-size" v-if="movie.overview!=''">
                    <div class="tagline" v-if="movie.tagline!=''">{{ movie.tagline }}</div>
                    <div class="overview">{{ movie.overview }}</div>
                </div>
            </div>
        </div>

        <div class="statistics-area">
            <div class="avg-rate">
                <div class="emoji">⭐️</div>
                <div class="value">{{ movie.avgRate }}</div>
                <div class="discription">{{ movie.rateCount+"명의 평가" }}</div>
            </div>
            <div class="wish-count">
                <div class="emoji">🙏</div>
                <div class="value">{{  movie.wishCount+"명" }}</div>
                <div class="discription">보고싶어요</div>
            </div>
            <div class="watcing-count">
                <div class="emoji">😎</div>
                <div class="value">{{ movie.watchingCount+"명" }}</div>
                <div class="discription">보는중</div>
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
#detail {
    display: flex;
    flex-direction: column;
    gap: 72px;
}
.movie-area {
    display: flex;
    flex-wrap: wrap-reverse;
    gap: 20px;
}
.poster, .info {
    display: flex;
    flex-direction: column;
    gap: 48px;
    justify-content: space-between;
}
.poster {
    flex-basis: 300px;
}
.info {
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
.poster img {
    max-width: 300px;
    object-fit: cover;
    margin: auto;
    border: 1px solid var(--G100);
    border-radius: 4px;
}
.tagline {
    font-weight: 600;
    font-size: 18px;
    margin-bottom: 12px;
}

.statistics-area {
    display: flex;
    justify-content: center;
    gap: 30px;
}
.statistics-area > * {
    display: flex;
    flex-direction: column;
    text-align: center;
    flex-shrink: 0;
}
.discription {
    color: var(--G500);
    line-height: 1.4;
}
.emoji {
    font-size: 36px;
    font-weight: 700;
    line-height: 1.4;
}
.value {
    font-size: 18px;
    line-height: 1.4;
    font-weight: 700;
}
</style>