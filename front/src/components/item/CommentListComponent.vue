<template>
    <transition-group name="list" tag="div" class="inner" v-if="this.list">
        <router-link class="item" v-for="(comment) in this.list" :key="comment" :to="`/content/comment/${comment.id}`">
            <div class="profile">
                <div v-if="comment.profileImage" class="profile-image" :style="{'background-image': 'url(' + comment.profileImage + ')' }"></div>
                <div class="no-image" v-if="!comment.profileImage"><i class="fa-solid fa-user"></i></div>
                <div class="nickname">{{ comment.nickname ? comment.nickname : comment.userId }}</div>
                <div class="status" v-if="comment.rate!=0 || comment.wish || comment.watching">
                    {{ comment.rate!=0 ? "⭐️ "+comment.rate : comment.wish ? "🙏 보고싶어요" : comment.watching ? "😎 보는중" : "" }}
                </div>
            </div>
            <div class="content">
                <div class="text" v-if="!comment.spoiler">{{ comment.content }}</div>
                <div class="text spoiler" v-if="comment.spoiler" @click.prevent="">
                    스포일러가 있어요!
                    <div class="view-spoiler" @click.prevent="viewSpoiler(comment)">보기</div>
                </div>
                <div class="movie" v-if="this.movie">
                    <img class="poster" :src="`/upload/poster/${comment.moviePosterPath}`">
                    <div class="info">
                        <div class="title">{{ comment.movieTitle }}</div>
                        <div class="genre">{{ [comment.movieGenre, comment.movieReleaseDate.substring(0,4)].join(" ・ ") }}</div>
                        <div class="overview">{{ comment.movieOverview }}</div>
                    </div>
                </div>
            </div>
            <div class="interest">
                <div class="like" :class="{ 'liked' : comment.userLike }"><span>👍</span> {{ comment.likeCount }}</div>
                <div class="reply">💬 {{ comment.replyCount }}</div>
            </div>
        </router-link>
    </transition-group>
</template>

<script>
export default {
    props: [
        'list',
        'movie',
    ],
    methods: {
        viewSpoiler(comment) {
            comment.spoiler = false;
        }
    }
}
</script>

<style scoped>
.inner {
    display: flex;
    flex-direction: column;
    gap: 24px;
}
.item {
    border-radius: 8px;;
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
.content {
    flex-grow: 1;
    padding: 20px 0;
    border-top: 1px solid var(--G100);
    border-bottom: 1px solid var(--G100);
    display: flex;
    flex-direction: column;
    gap: 24px;
}
.spoiler {
    display: flex;
    gap: 4px;
}
.view-spoiler {
    color: var(--FOCUS);
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
    color: var(--G700);
    font-weight: 600;
    padding: 3px 10px;
    font-size: 14px;
    flex-shrink: 0;
}
.dark .status {
    background: var(--G100);
    /* border: none; */
}
.movie {
    display: flex;
    overflow: hidden;
    border-radius: 8px;
    background: var(--G100);
    margin: 0 8px;
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
.movie .poster {
    max-width: 90px;
    border-right: 1px solid var(--G100);
}

.profile-image, .no-image {
    width: 36px;
    height: 36px;
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