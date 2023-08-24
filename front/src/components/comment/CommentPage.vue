<template>

    <div id="comment" class="container" v-if="this.comment">
        <div class="inner">
            <div class="profile">
                <div v-if="comment.profileImage" class="profile-image" :style="{'background-image': 'url(' + comment.profileImage + ')' }"></div>
                <div class="no-image" v-if="!comment.profileImage">👤</div>
                <div>
                    <div class="nickname">{{ comment.nickname ? comment.nickname : comment.userId }}</div>
                    <div class="create-date">{{ comment.creDate.substr(0,10).replace(/-/g, ".") }}</div>
                </div>
            </div>
            <div class="status" v-if="comment.rate!=0 || comment.wish || comment.watching">
                {{ comment.rate!=0 ? "⭐️ "+comment.rate : comment.wish ? "🙏 보고싶어요" : comment.watching ? "😎 보는중" : "" }}
            </div>
            <div class="content">
                <div class="text" v-html="comment.content.replace(/(?:\r\n|\r|\n)/g, '<br/>')"></div>
            </div>
        </div>
        <div class="inner">
            <div class="movie" v-if="this.movie">
                <img class="poster" :src="movie.poster_path" @click="$router.push({ path: '/detail/'+this.movie.id })">
                <div class="info">
                    <div class="title">{{ movie.title }}</div>
                    <div class="genre">{{ movie.genres }}</div>
                    <router-link :to="`/detail/${this.movie.id}/comment`" class="more-comment">👉 {{ movie.title }}의 다른 코멘트 보러가기</router-link>
                </div>
            </div>
        </div>

        <reply-list-component
        v-bind:comment="comment"
        @updateUserLike="this.updateUserLike"></reply-list-component>
        
    </div>
</template>

<script>
import movieAxios from '@/axios/movieAxios';
import ReplyListComponent from './ReplyListComponent.vue';

export default {
    components: {
        ReplyListComponent,
    },
    async created() {
        const params = {
            id: this.id,
        }
        try {
            // 코멘트 정보
            const comment = await this.axios.get('/comment', {params});
            this.comment = comment.data;
            // 영화 정보
            const movie = await movieAxios.get('api.themoviedb.org/3/movie/'+this.comment.movieId+'?api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR');
            this.movie = movie.data;
        } catch(error) {
            this.$router.push('/error');
        }
    },
    data() {
        return {
            id: this.$route.params.id,
            comment: null,
            movie: null,
        }
    },
    watch: {
        // 영화 상세정보 가공
        movie() {
            this.movie.release_date = this.movie.release_date.split('-').join('.');
            if(this.movie.backdrop_path) {
                this.movie.backdrop_path = 'https://image.tmdb.org/t/p/original/'+this.movie.backdrop_path;
            }
            if(this.movie.poster_path) {
                this.movie.poster_path = 'https://image.tmdb.org/t/p/original/'+this.movie.poster_path;
            }
            var genre = [];
            for(var g of this.movie.genres) {
                genre.push(g.name);
            }
            this.movie.genres = genre.join('/');
        },
    },
    methods: {
        updateUserLike(param) {
            console.log(param);
            this.comment.userLike = param;
        }
    }
}
</script>

<style scoped>
.container {
    max-width: 1280px;
    display: flex;
    flex-direction: column;
    gap: 60px;
}
.inner {
    display: flex;
    flex-direction: column;
    gap: 30px;
}
.profile {
    display: flex;
    align-items: center;
    gap: 8px;
}
.profile-image, .no-image {
    width: 36px;
    height: 36px;
    background-size: cover;
    background-position: center;
    border-radius: 50%;
    background-color: var(--G100);
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
}
.no-image {
    font-size: 16px;
}
.nickname {
    /* font-size: 18px; */
    font-weight: 500;
}
.create-date {
    color: var(--G400);
    font-size: 14px;
}
.status {
    border-radius: 16px;
    border: 1px solid var(--G200);
    padding: 3px 10px;
    font-size: 14px;
    flex-shrink: 0;
    margin-right: auto;
}
.dark .status {
    background: var(--G100);
    /* border: none; */
}
.content {
    display: flex;
    flex-direction: column;
    gap: 16px;
}
.movie {
    display: flex;
    gap: 16px;
}
.poster {
    width: 80px;
    object-fit: cover;
    cursor: pointer;
}
.movie .info {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
}
.movie .title {
    font-weight: 700;
}
.movie .genre {
    color: var(--G400);
    font-size: 14px;
    flex-grow: 1;
}
.movie .more-comment {
    cursor: pointer;
    font-weight: 500;
}
.interest {
    display: flex;
    gap: 16px;
}
</style>