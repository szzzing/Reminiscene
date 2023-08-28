<template>
    <div id="wish" class="container">
        <title-component>
            <template v-slot:emoji>🙏</template>
            <template v-slot:title>
                {{ user.nickname=='' ? user.id : user.nickname }}
                님이<br>작성한 코멘트
            </template>
        </title-component>

        <transition-group name="list" tag="div" class="inner">
            <router-link class="item" v-for="(comment) in this.list" :key="comment" :to="`/comment/${comment.id}`" @click="console.log(comment.movie)">
                <div class="movie" v-if="comment.movie">
                    <div v-if="comment.profileImage" class="profile-image" :style="{'background-image': 'url(' + comment.profileImage + ')' }"></div>
                    <div class="no-image" v-if="!comment.profileImage">👤</div>

                    <div class="movie-title">{{ comment.movie.title }} {{ comment.movie.release_date!='' ? "("+comment.movie.release_date.split('-')[0]+")" : '' }}</div>
                    <div class="status" v-if="comment.rate!=0 || comment.wish || comment.watching">
                        {{ comment.rate!=0 ? "⭐️ "+comment.rate : comment.wish ? "🙏 보고싶어요" : comment.watching ? "😎 보는중" : "" }}
                    </div>
                </div>
                <div class="text">{{ comment.content }}</div>
                <div class="interest">
                    <div class="like">👍 {{ comment.likeCount }}</div>
                    <div class="reply">💭 {{ comment.replyCount }}</div>
                </div>
            </router-link>
        </transition-group>
        <infinite-loading @infinite="getList"></infinite-loading>
    </div>
</template>

<script>
import InfiniteLoading from 'infinite-loading-vue3-ts';
import TitleComponent from '../item/TitleComponent.vue';
import movieAxios from '@/axios/movieAxios';

export default {
    components: {
        TitleComponent,
        InfiniteLoading,
    },

    data() {
        return {
            page: 1,
            list: [],
            user: this.$store.state.auth.user,
        }
    },

    methods: {
        getList($state) {
            const params = {
                userId: this.$store.state.auth.user.id,
                page: this.page,
            }
            this.axios.get("/comments", {params})
            .then((response)=>{
                if(response.data.list.length!=0) {
                    const list = response.data.list;
                    this.page = response.data.page + 1;
                    for(const l of list) {
                        movieAxios.get('api.themoviedb.org/3/movie/'+l.movieId+'?api_key=7bf40bf859def4eaf9886f19bb497169&language=ko-KR')
                        .then((response)=>{
                            l.movie = response.data;
                            this.list.push(l);
                        });
                    }
                    $state.loaded();
                } else {
                    $state.complete();
                }
            })
        }
    }
}
</script>

<style scoped>
.container {
    max-width: 960px;
    display: flex;
    flex-direction: column;
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
.movie {
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

.movie-title {
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
</style>