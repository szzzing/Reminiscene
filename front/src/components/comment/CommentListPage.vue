<template>
    <div id="comment-list" class="container">
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
                    <div class="like">👍 {{ comment.likeCount }}</div>
                    <div class="reply">💭 {{ comment.replyCount }}</div>
                </div>
            </router-link>
        </transition-group>
        <div class="view-more small-button" @click="this.page = this.page+1">더보기</div>
    </div>
</template>

<script>
export default {
    created() {
        this.getComment();
    },
    data() {
        return {
            user: this.$store.state.auth.user,
            page: 1,
            sort: null,
            list: [],
        }
    },
    watch: {
        page() {
            if(this.page==1) {
                this.list = [];
                this.getComment();
            } else {
                this.getComment();
            }
        }
    },
    methods: {
       getComment() {
            const params = {
                page: this.page,
                sort: this.sort,
            }
            this.axios.get("/movie/"+this.$route.params.id+"/comment", {params})
            .then((response)=>{
                for(var c of response.data.list) {
                    this.list.push(c);
                }
            })
        },
    },
}
</script>

<style scoped>
.container {
    max-width: 800px;
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

.inner {
    display: flex;
    flex-direction: column;
    gap: 24px;
}

.small-button {
    width: 80px;
    margin: 60px auto 0;
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
</style>