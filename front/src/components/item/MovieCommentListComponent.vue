<template>
    <transition-group name="list" tag="div" class="inner" v-if="this.list">
        <router-link class="item" v-for="(comment) in this.list" :key="comment" :to="`/comment/${comment.id}`">
            <img class="poster" :src="`/upload/poster/${comment.moviePosterPath}`">
            <div class="comment">
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
                </div>
                <div class="interest">
                    <div class="like" :class="{ 'liked' : comment.userLike }"><span>👍</span> {{ comment.likeCount }}</div>
                    <div class="reply">💬 {{ comment.replyCount }}</div>
                </div>
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
    border-radius: 8px;
    display: flex;
    gap: 20px;
    cursor: pointer;
    background: var(--G50);
    overflow: hidden;
}
.poster {
    object-fit: cover;
    border: 1px solid var(--G50);
    border-top-left-radius: 8px;
    border-bottom-left-radius: 8px;
    max-width: 120px;
}
.comment {
    flex-grow: 1;
    padding: 16px 20px 16px 0;
    display: flex;
    flex-direction: column;
}
.profile {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: 12px;
}
.content {
    padding: 12px 0;
    border-top: 1px solid var(--G100);
    border-bottom: 1px solid var(--G100);
    flex-grow: 1;
}
.text {
    text-overflow: ellipsis;
    overflow: hidden;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
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
    margin-top: 12px;
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