<template>

    <div id="comment" class="container" v-if="this.comment">
        <div class="inner">
            <div class="profile" v-if="comment.userEnable">
                <router-link v-if="comment.profileImage" class="profile-image" :style="{'background-image': `url(${comment.profileImage})` }" :to="`/user/${comment.userId}`"></router-link>
                <router-link class="no-image" v-if="!comment.profileImage" :to="`/user/${comment.userId}`"><i class="fa-solid fa-user"></i></router-link>
                <div>
                    <div class="nickname">{{ comment.nickname ? comment.nickname : comment.userId }}</div>
                    <div class="create-date">{{ comment.creDate.substr(0,10).replace(/-/g, ".") }}</div>
                </div>
            </div>
            <div class="profile" v-if="!comment.userEnable">
                <div v-if="comment.profileImage" @click="withdrawUser" class="profile-image" :style="{'background-image': `url(${comment.profileImage})` }"></div>
                <div class="no-image" @click="withdrawUser" v-if="!comment.profileImage"><i class="fa-solid fa-user"></i></div>
                <div>
                    <div class="nickname">{{ comment.nickname ? comment.nickname : comment.userId }}</div>
                    <div class="create-date">{{ comment.creDate.substr(0,10).replace(/-/g, ".") }}</div>
                </div>
            </div>

            <div class="status" v-if="comment.rate!=0 || comment.wish || comment.watching">
                {{ comment.rate!=0 ? "⭐️ "+comment.rate : comment.wish ? "🙏 보고싶어요" : comment.watching ? "😎 보는중" : "" }}
            </div>
            <div class="content">
                <div class="text">{{ comment.content }}</div>
            </div>
        </div>
        <div class="inner">
            <div class="movie">
                <img class="poster" :src="`/upload/poster/${comment.moviePosterPath}`" @click="$router.push({ path: '/detail/'+comment.movieId })">
                <div class="info">
                    <div class="title">{{ comment.movieTitle }}</div>
                    <div class="genre">{{ comment.movieGenre }}</div>
                    <router-link :to="`/detail/${comment.movieId}/comment`" class="more-comment">👉 {{ comment.movieTitle }}의 다른 코멘트 보러가기</router-link>
                </div>
            </div>
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
        }
    },
    methods: {
        fetchData() {
            // 코멘트 정보
            this.axios.get(`/comment/${this.$route.params.id}`)
            .then((response)=>{
                this.comment = response.data;
            });
        },
        updateUserLike(value) {
            this.comment.userLike = value;
        },
        withdrawUser() {
            this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"탈퇴한 사용자예요." });
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
    gap: 8px;
}
.profile-image, .no-image {
    width: 36px;
    height: 36px;
    background-size: cover;
    background-position: center;
    border-radius: 50%;
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