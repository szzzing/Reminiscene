<template>
    <div id="comment" v-if="this.movie">
        <div class="inner">
            <div class="title">
                코멘트
                <span class="sub-title">{{ this.listCount }}</span>
            </div>
            <router-link class="view-more" :to="`/detail/${this.movie.id}/comment`">더보기</router-link>
        </div>
        <transition-group name="list" tag="div" class="best-comment">
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
                    <div class="like">👍 {{ comment.likeCount }}</div>
                    <div class="reply">💭 {{ comment.replyCount }}</div>
                </div>
            </router-link>
        </transition-group>
    </div>
</template>

<script>
export default {
    created() {
        this.getFamousComment();
    },
    data() {
        return {
            user: this.$store.state.auth.user,
            list: [],
            listCount: null,
        }
    },
    props: [
        'movie',
    ],
    methods: {
        getFamousComment() {
            const params = {
                page: 1,
                sort: "famous",
                movieId: this.$route.params.id,
            }
            this.axios.get("/comments", {params})
            .then((response)=>{
                this.listCount = response.data.listCount;
                for(var c of response.data.list) {
                    this.list.push(c);
                }
            })
        },
    },
}
</script>

<style scoped>
#comment {
    display: flex;
    flex-direction: column;
    gap: 24px;
}
.inner {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.title {
    font-size: 24px;
    font-weight: 700;
}
.sub-title {
    font-weight: 400;
    color: var(--FOCUS);
    margin-left: 4px;
}

.best-comment {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
}
.view-more {
    color: var(--G400);
}
.item {
    border-radius: 16px;
    flex-basis: calc((100% - 60px)/4);
    padding: 20px 16px;
    display: flex;
    flex-direction: column;
    gap: 20px;
    cursor: pointer;
    background: var(--G50);
}
@media screen and (max-width:960px) {
    .item {
        flex-basis: calc((100% - 40px)/3);
    }
    .item:nth-child(3) ~ .item {
        display: none;
    }
}
@media screen and (max-width:800px) {
    .item {
        flex-basis: calc((100% - 20px)/2);
    }
    .item:nth-child(2) ~ .item {
        display: none;
    }
}
@media screen and (max-width:500px) {
    .item {
        flex-basis: 100%;
    }
    .item:nth-child(1) ~ .item {
        display: none;
    }
}
.profile {
    display: flex;
    align-items: center;
    gap: 8px;
    padding-bottom: 20px;
    border-bottom: 1px solid var(--G100);
}
.text {
    flex-grow: 1;
    text-overflow: ellipsis;
    overflow: hidden;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 7;
}
.interest {
    display: flex;
    gap: 16px;
    padding-top: 20px;
    border-top: 1px solid var(--G100);
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
</style>