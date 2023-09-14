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
        console.log(this.elapsedTime("2022-08-21T18:18:34.000+00:00"));
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