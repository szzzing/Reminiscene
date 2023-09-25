<template>

    <div id="comment" class="container" v-if="this.comment">
        <div class="inner">
            <div class="profile">
                <router-link v-if="comment.userEnable && comment.profileImage" class="profile-image" :style="{'background-image': `url(${comment.profileImage})` }" :to="`/user/${comment.userId}`"></router-link>
                <router-link v-if="comment.userEnable && !comment.profileImage" class="no-image" :to="`/user/${comment.userId}`"><i class="fa-solid fa-user"></i></router-link>
                <div v-if="!comment.userEnable && comment.profileImage" @click="withdrawUser" class="profile-image" :style="{'background-image': `url(${comment.profileImage})` }"></div>
                <div v-if="!comment.userEnable && !comment.profileImage" class="no-image" @click="withdrawUser"><i class="fa-solid fa-user"></i></div>
                <div>
                    <div class="nickname">{{ comment.nickname ? comment.nickname : comment.userId }}</div>
                    <div class="create-date">{{ this.elapsedTime(comment.creDate) }} {{ comment.creDate==comment.modDate ? "" : "・ 수정됨" }}</div>
                </div>
            </div>

            <div class="status">
                <div class="item" v-if="comment.rate!=0">
                    {{ "⭐️ "+comment.rate }}
                </div>
                <div class="item" v-if="comment.watching">
                    {{ "😎 보는 중" }}
                </div>
                <div class="item" v-if="comment.wish">
                    {{ "🙏 보고싶어요" }}
                </div>
            </div>

            <div class="content">
                <div class="text">{{ comment.content }}</div>
            </div>
        </div>
        <div class="inner">
            <router-link class="movie item-border" v-if="this.movie" :to="`/detail/${movie.id}`">
                <img class="poster" :src="`/upload/poster/${comment.moviePosterPath}`">
                <div class="info">
                    <div class="title">{{ movie.title }}</div>
                    <div class="genre">{{ [movie.genre, movie.releaseDate.substring(0,4)].join(" ・ ") }}</div>
                    <div class="overview">{{ movie.overview }}</div>
                </div>
            </router-link>
        </div>

        <reply-list-component
        v-bind:comment="comment"
        v-on:updateUserLike="this.updateUserLike"></reply-list-component>
        
    </div>
</template>

<script>
import ReplyListComponent from './ReplyListComponent.vue';

export default {
    components: {
        ReplyListComponent,
    },
    async created() {
        this.fetchData();
    },
    data() {
        return {
            comment: null,
            movie: null,
        }
    },
    methods: {
        fetchData() {
            // 코멘트 정보
            this.axios.get(`/comment/${this.$route.params.id}`)
            .then((response)=>{
                this.comment = response.data;
                this.axios.get(`/movie/${this.comment.movieId}`)
                .then((response)=>{
                    this.movie = response.data;
                })
            });
        },
        updateUserLike(value) {
            this.comment.userLike = value;
        },
        // 탈퇴 사용자 프로필 클릭
        withdrawUser() {
            this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"탈퇴한 사용자예요." });
        },
        // 현재 기준 시간
        elapsedTime(date) {
            const start = new Date(date);
            const end = new Date();

            const diff = (end - start) / 1000;
            
            const times = [
                { name: '년', milliSeconds: 60 * 60 * 24 * 365 },
                { name: '개월', milliSeconds: 60 * 60 * 24 * 30 },
                { name: '일', milliSeconds: 60 * 60 * 24 },
                { name: '시간', milliSeconds: 60 * 60 },
                { name: '분', milliSeconds: 60 },
            ];

            for (const value of times) {
                const betweenTime = Math.floor(diff / value.milliSeconds);

                if (betweenTime > 0) {
                return `${betweenTime}${value.name} 전`;
                }
            }
            return '방금 전';
        },
    },
    watch: {
        '$route.params.id': 'fetchData',
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
    gap: 12px;
}
.profile-image, .no-image {
    width: 40px;
    height: 40px;
    background-size: cover;
    background-position: center;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
}
.no-image {
    font-size: 20px;
}
.nickname {
    font-weight: 500;
}
.create-date {
    color: var(--G400);
    font-size: 14px;
}
.status {
    flex-shrink: 0;
    margin-right: auto;
    display: flex;
    gap: 8px;
}
.status .item {
    border-radius: 16px;
    border: 1px solid var(--G200);
    color: var(--G700);
    font-weight: 600;
    padding: 3px 10px;
    font-size: 14px;
    flex-shrink: 0;
    margin-right: auto;
}
.dark .status .item {
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
    border-radius: 8px;
    background: var(--G50);
    overflow: hidden;
}
.poster {
    width: 100px;
    object-fit: cover;
    cursor: pointer;
}
.movie .info {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    padding: 16px;
}
.movie .title {
    font-weight: 600;
}
.movie .genre {
    color: var(--G400);
    font-size: 14px;
    flex-grow: 1;
}
.movie .overview {
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    color: var(--G600);
    font-size: 14px;
}
.interest {
    display: flex;
    gap: 16px;
}
</style>