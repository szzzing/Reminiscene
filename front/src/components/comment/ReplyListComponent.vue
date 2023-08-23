<template>
    <reply-modal-component v-bind:comment="comment"
    v-if="this.comment && this.replyModal"
    v-on:closeReplyModal="this.replyModal=false"
    v-on:reloadReply="this.page=null">
    </reply-modal-component>

    <div class="inner">
        <div class="like-reply">
            <div class="like-button">👍 좋아요</div>
            <div class="reply-button" @click="this.clickReply()">💭 댓글</div>
        </div>
    </div>

    <transition-group name="list" tag="div" id="reply-list">
        <div class="reply" v-for="(reply) in this.list" :key="reply">
            <div v-if="reply.profileImage" class="profile-image" :style="{'background-image': 'url(' + reply.profileImage + ')' }"></div>
            <div v-if="!reply.profileImage" class="no-image">👤</div>
            <div class="text-area">
                <div class="info">
                    <div class="nickname">{{ reply.nickname ? reply.nickname : reply.userId }}</div>
                    <div class="option">
                        <div class="modify-button">수정</div>
                        <div class="modify-button">삭제</div>
                    </div>
                </div>
                <div class="content" v-html="reply.content.replace(/(?:\r\n|\r|\n)/g, '<br/>')"></div>
                <div class="create-date">{{ reply.creDate.substr(0,10).replace(/-/g, ".") }}</div>
            </div>
        </div>
    </transition-group>
</template>

<script>
import ReplyModalComponent from './ReplyModalComponent.vue';

export default {
    components: {
        ReplyModalComponent,
    },
    created() {
        this.getReply();
    },
    data() {
        return {
            refId: this.$route.params.id,
            page: null,
            list: [],
            replyModal: false,
        }
    },
    props: [
        'comment',
    ],
    watch: {
        page() {
            this.getReply();
        }
    },
    methods: {
        getReply() {
            const params = {
            refId: this.refId,
            }
            this.axios.get("/comment/"+this.refId+"/reply", {params})
            .then((response)=>{
                this.page = response.data.page;
                if(this.page==1) {
                    this.list = [];
                }
                for(var r of response.data.list) {
                    this.list.push(r);
                }
            });
        },
        clickReply() {
            if(this.$store.state.auth.user) {
                this.replyModal = true;
            } else {
                this.$store.commit("modal/setAlert", { alertEmoji: "✋", alertText: "로그인 후 이용해주세요." });
            }
        },
    },
}
</script>

<style scoped>
.reply {
    display: flex;
    gap: 16px;
    padding: 16px;
    border-bottom: 1px solid var(--G100);
}
.reply:last-child {
    border-bottom: none;
}
.text-area {
    display: flex;
    flex-direction: column;
    gap: 8px;
    flex-grow: 1;
}
.info {
    display: flex;
    justify-content: space-between;
}
.option {
    display: flex;
    gap: 8px;
}
.option > * {
    font-size: 14px;
    color: var(--G400);
    cursor: pointer;
}
.nickname {
    font-weight: 500;
}
.profile {
    display: flex;
    align-items: center;
    gap: 8px;
}
.profile-image, .no-image {
    width: 30px;
    height: 30px;
    background-size: cover;
    background-position: center;
    border-radius: 50%;
    background-color: var(--G100);
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
}
.create-date {
    color: var(--G400);
    font-size: 14px;
}
.like-reply {
    display: flex;
    justify-content: space-between;
    border-top: 1px solid var(--G200);
    border-bottom: 1px solid var(--G200);
    gap: 2px;
}
.like-reply > * {
    flex-grow: 1;
    text-align: center;
    border-radius: 4px;
    padding: 12px 0;
    margin: 4px 0;
    cursor: pointer;
    transition: all 0.2s;
}
.reply-button:hover,
.like-button:hover {
    background: var(--G50);
}
</style>