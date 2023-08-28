<template>
    <div id="comment-list" class="container" v-if="this.movie && this.list">

        <title-component>
            <template v-slot:emoji>📃</template>
            <template v-slot:title>
                {{ this.movie.title }}의
                <br>코멘트
            </template>
        </title-component>

        <transition-group name="list" tag="div" class="inner">
            <router-link class="item" v-for="(comment) in this.list" :key="comment" :to="`/comment/${comment.id}`">
                <div class="profile">
                    <div v-if="comment.profileImage" class="profile-image" :style="{'background-image': 'url(' + comment.profileImage + ')' }"></div>
                    <div class="no-image" v-if="!comment.profileImage">👤</div>
                    <div class="nickname">{{ comment.nickname ? comment.nickname : comment.userId }}</div>
                    <div class="status" v-if="comment.rate!=0 || comment.wish || comment.watching">
                        {{ comment.rate!=0 ? "⭐️ "+comment.rate : comment.wish ? "🙏 보고싶어요" : comment.watching ? "😎 보는중" : "" }}
                    </div>
                </div>
                <div class="text">{{ comment.content }}</div>
                <div class="interest">
                    <div class="like" :class="{ 'liked' : comment.userLike }"><span>👍</span> {{ comment.likeCount }}</div>
                    <div class="reply">💬 {{ comment.replyCount }}</div>
                </div>
            </router-link>
        </transition-group>
    </div>

    <infinite-loading @infinite="getComment"></infinite-loading>
</template>

<script>
import movieAxios from '@/axios/movieAxios';
import { InfiniteLoading } from 'infinite-loading-vue3-ts';
import TitleComponent from '../item/TitleComponent.vue';

export default {
    components: {
        InfiniteLoading,
        TitleComponent,
    },
    created() {
        this.getMovie();
    },
    data() {
        return {
            user: this.$store.state.auth.user,
            page: 1,
            sort: null,
            list: [],
            movie: null,
        }
    },
    methods: {
        getMovie() {
            movieAxios.get('api.themoviedb.org/3/movie/'+this.$route.params.id+'?api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR')
            .then((response)=>{
                this.movie = response.data;
            });
        },
        getComment($state) {
            const params = {
                page: this.page,
                sort: this.sort,
                movieId: this.$route.params.id,
            }
            this.axios.get("/comments", {params})
            .then((response)=>{
                console.log(response.data);
                if(response.data.list.length!=0) {
                    this.list.push(...response.data.list);
                    this.page = response.data.page + 1;
                    $state.loaded();
                } else {
                    $state.complete();
                }
            })
        },
    },
}
</script>

<style scoped>
.container {
    max-width: 960px;
    display: flex;
    flex-direction: column;
}
.title {
    font-size: 24px;
    font-weight: 700;
}

.inner {
    display: flex;
    flex-direction: column;
    gap: 24px;
}

.item {
    border-radius: 16px;
    padding: 20px 16px;
    display: flex;
    flex-direction: column;
    gap: 20px;
    cursor: pointer;
    background: var(--G50);
}
.profile {
    display: flex;
    align-items: center;
    gap: 8px;
}
.spoiler {
    filter: blur(8px);
}
.text {
    flex-grow: 1;
    padding: 20px 0;
    border-top: 1px solid var(--G100);
    border-bottom: 1px solid var(--G100);
}
.interest {
    display: flex;
    gap: 16px;
}

.nickname {
    flex-grow: 1;
    font-weight: 500;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.status {
    border-radius: 16px;
    border: 1px solid var(--G200);
    background: var(--G0);
    padding: 3px 10px;
    font-size: 14px;
    flex-shrink: 0;
}
.dark .status {
    background: var(--G100);
    /* border: none; */
}

.profile-image, .no-image {
    width: 30px;
    height: 30px;
    background-size: cover;
    background-position: center;
    border-radius: 50%;
    background-color: var(--G200);
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
}
.no-image {
    font-size: 16px;
}
.interest * {
    color: var(--G500);
    font-size: 14px;
}
.like span {
    opacity: 0.5;
    filter: grayscale(1);
}
.liked span {
    opacity: 1;
    filter: none;
}
</style>