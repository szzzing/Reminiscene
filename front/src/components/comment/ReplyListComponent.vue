<template>
    <!-- 댓글 작성 모달 -->
    <reply-modal-component v-bind:comment="comment"
    v-if="this.comment && this.replyModal"
    v-on:closeReplyModal="this.replyModal=false"
    v-on:reloadReply="this.reloadReply">
    </reply-modal-component>

    <!-- 댓글 수정 모달 -->
    <modify-modal-component v-bind:reply="reply"
    v-if="this.reply && this.modifyModal"
    v-on:closeModifyModal="this.modifyModal=false"
    v-on:updateReply="this.updateReply">
    </modify-modal-component>

    <!-- 댓글 삭제 모달 -->
    <delete-modal-component v-bind:reply="reply"
    v-if="this.reply && this.deleteModal"
    v-on:closeDeleteModal="this.deleteModal=false"
    v-on:removeReply="this.removeReply">
    </delete-modal-component>

    <!-- 댓글 신고 모달 -->
    <report-modal-component v-bind:reply="reply"
    v-if="this.reply && this.reportModal"
    v-on:closeReportModal="this.reportModal=false">
    </report-modal-component>

    <div class="inner">
        <div class="like-reply">
            <div class="like-button" @click="this.clickLike()" :class="{'selected' : this.comment.userLike}">
                <div class="emoji">👍</div>
                <div class="text">좋아요</div>
            </div>
            <div class="reply-button" @click="this.clickReply()">
                <div class="emoji">💬</div>
                <div class="text">댓글</div>
            </div>
        </div>
    </div>

    <div class="inner">
        <transition-group name="list" tag="div" id="reply-list">
            <div class="reply" v-for="(reply) in this.list" :key="reply">
                <router-link v-if="reply.profileImage" class="profile-image" :style="{'background-image': `url(${reply.profileImage})` }" :to="`/user/${reply.userId}`"></router-link>
                <router-link class="no-image" v-if="!reply.profileImage" :to="`/user/${reply.userId}`"><i class="fa-solid fa-user"></i></router-link>
                <div class="text-area">
                    <div class="info">
                        <div class="nickname">{{ reply.nickname ? reply.nickname : reply.userId }}{{ reply.userId==this.comment.userId ? " (작성자)" : "" }}</div>
                        <div class="option">
                            <div class="modify-button" @click="this.clickModify(reply)" v-if="this.$store.state.auth.user && this.$store.state.auth.user.id==reply.userId">수정</div>
                            <div class="delete-button" @click="this.clickDelete(reply)" v-if="this.$store.state.auth.user && this.$store.state.auth.user.id==reply.userId">삭제</div>
                            <div class="report-button" @click="this.clickReport(reply)" v-if="this.$store.state.auth.user && this.$store.state.auth.user.id!=reply.userId">신고</div>
                        </div>
                    </div>
                    <div class="content">{{ reply.content }}</div>
                    <div class="create-date">{{ reply.creDate.substr(0,10).replace(/-/g, ".") }}</div>
                </div>
            </div>
        </transition-group>
        <empty-component v-if="this.list.length==0">
            <template v-slot:text>
                {{ this.comment.nickname ? this.comment.nickname : this.comment.userId }}님의 리뷰에 첫번째 댓글을 달아주세요.
            </template>
        </empty-component>
        <infinite-loading @infinite="getReply"></infinite-loading>
    </div>
</template>

<script>
import { InfiniteLoading } from 'infinite-loading-vue3-ts';
import DeleteModalComponent from './DeleteModalComponent.vue';
import ModifyModalComponent from './ModifyModalComponent.vue';
import ReplyModalComponent from './ReplyModalComponent.vue';
import ReportModalComponent from './ReportModalComponent.vue';
import EmptyComponent from '../item/EmptyComponent.vue';

export default {
    components: {
        ReplyModalComponent,
        ModifyModalComponent,
        DeleteModalComponent,
        ReportModalComponent,
        EmptyComponent,
        InfiniteLoading,
    },
    data() {
        return {
            state: null,
            page: 1,
            list: [],
            reply: null,
            replyModal: false,
            modifyModal: false,
            deleteModal: false,
            reportModal: false,
        }
    },
    props: [
        'comment',
    ],

    emits: [
        'updateUserLike',
    ],

    methods: {
        // 댓글 조회
        getReply($state) {
            this.state = $state;
            const params = {
                refId: this.$route.params.id,
                page: this.page,
            }
            this.axios.get("/reply", {params})
            .then((response)=>{
                if(!response.data.list.length==0) {
                    this.list.push(...response.data.list);
                    this.page = response.data.page + 1;
                    $state.loaded();
                }
            });
        },

        reloadReply() {
            this.fetchData();
        },

        //작성
        clickReply() {
            if(this.$store.state.auth.user) {
                this.replyModal = true;
            } else {
                this.$store.commit("modal/setAlert", { alertEmoji: "✋", alertText: "로그인 후 이용해주세요." });
            }
        },

        // 수정
        clickModify(reply) {
            this.reply = reply;
            this.modifyModal = true;
        },
        updateReply(params) {
            this.list.find(e => e.id===params.id).content = params.content;
        },

        // 삭제
        clickDelete(reply) {
            this.reply = reply;
            this.deleteModal = true;
        },
        removeReply(replyId) {
            const index = this.list.findIndex(e => e.id===replyId);
            this.list.splice(index, 1);
        },

        // 좋아요
        clickLike() {
            if(this.$store.state.auth.user) {
                // 삭제
                if(this.comment.userLike) {
                    this.deleteLike();
                    // 추가
                } else {
                    this.addLike();
                }
            } else {
                this.$store.commit("modal/setAlert", { alertEmoji: "✋", alertText: "로그인 후 이용해주세요." });
            }
        },
        addLike() {
            const params = {
                userId : this.$store.state.auth.user.id,
                commentId : this.comment.id,
            }
            this.axios.post('/like', params)
            .then(()=>{
                this.$emit("updateUserLike", true);
                this.$store.commit("modal/setAlert", { alertEmoji: "😃", alertText: "이 코멘트를 좋아해요." });
            });
        },
        deleteLike() {
            const userId = this.$store.state.auth.user.id;
            this.axios.delete('/like/'+this.comment.id, {userId})
            .then(()=>{
                this.$emit("updateUserLike", false);
                this.$store.commit("modal/setAlert", { alertEmoji: "😃", alertText: "이 코멘트의 좋아요를 취소했어요." });
            });
        },
        // 신고
        clickReport(reply) {
            this.reply = reply;
            this.reportModal = true;
        },

        // 데이터 리로드
        fetchData() {
            const params = {
                refId: this.$route.params.id,
                page: 1,
            }
            this.axios.get("/reply", {params})
            .then((response)=>{
                if(!response.data.list.length==0) {
                    this.list = response.data.list;
                    this.page = response.data.page + 1;
                    this.state.loaded();
                }
            });
        },
    },
    watch: {
        '$route.params.id': 'fetchData',
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
    width: 36px;
    height: 36px;
    font-size: 16px;
}
.create-date {
    color: var(--G400);
    font-size: 20px;
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
    display: flex;
    gap: 8px;
    justify-content: center;
    align-items: center;
}
.like-reply * {
    transition: all 0.2s;
}
.like-reply .text {
    color: var(--G600);
}
.like-button .emoji {
    opacity: 0.5;
}
.selected.like-button .emoji,
.selected.like-button .text {
    opacity: 1;
    color: var(--FOCUS);
}
.reply-button:hover,
.like-button:hover {
    background: var(--G50);
}
.small-button {
    width: 80px;
    margin: 60px auto 0;
}
</style>