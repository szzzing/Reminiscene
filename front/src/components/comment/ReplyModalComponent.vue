<template>
    <transition name="smooth">
        <div id="reply-modal" class="modal-mask" @click="$emit('closeReplyModal')">
            <div class="modal-wrapper">
                <div class="modal-container item-fill" @click.stop="">
                    <div class="inner">
                        <div class="title">댓글</div>
                        <i class="close fa-solid fa-circle-xmark" @click="$emit('closeReplyModal')"></i>
                    </div>
                    <div class="inner">
                        <textarea class="text" v-model="this.text" @input="this.count($event)" placeholder="이 코멘트에 대한 생각을 자유롭게 표현해주세요." maxlength="1000"></textarea>
                    </div>
                    <div class="inner">
                        <div class="count-text">{{ this.textCount }}/1000</div>
                        <div class="medium-button" @click="this.addReply()" :class="{'disabled': this.textCount==0 || this.textCount>1000 }">저장</div>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>

<script>
export default {
    beforeCreate() {
        
    },
    data() {
        return {
            text: '',
            textCount: 0,
        }
    },
    props: [
        'comment',
    ],
    emits: [
        'reloadReply', 'closeReplyModal',
    ],
    watch: {
        textCount() {
            if(this.textCount==1000) {
                this.$store.commit("modal/setAlert", { alertEmoji: "⚠️", alertText: "최대 글자수를 채웠어요." });
            }
        }
    },
    methods: {
        count(event) {
            this.textCount = event.target.value.length;
        },
        addReply() {
            const params = {
                refId: this.comment.id,
                userId: this.$store.state.auth.user.id,
                content: this.text,
            };
            this.axios.post("/reply", params)
            .then(()=>{
                this.$emit('closeReplyModal');
                this.$store.commit("modal/setAlert", { alertEmoji: "😃", alertText: "댓글을 남겼어요." });
                this.$emit('reloadReply');
            });
        },
    }
}
</script>

<style scoped>
.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: table;
}
.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}
.modal-container {
    max-width: 480px;
    height: 480px;
    margin: 0 auto;
    padding: 20px;
    border-radius: 8px;;
    display: flex;
    flex-direction: column;
    gap: 16px;
}
.inner {
    display: flex;
    justify-content: space-between;
}
.inner:nth-child(2) {
    flex-grow: 1;
}
.inner:last-child {
    justify-content: flex-end;
    gap: 16px;
    align-items: center;
}
.title {
    flex-grow: 1;
    font-size: 18px;
    font-weight: 700;
}
.close {
    cursor: pointer;
}
.text {
    width: 100%;
    font-size: 18px;
}
.is-spoiler {
    flex-grow: 1;
    display: flex;
    align-items: center;
    gap: 4px;
}
.is-spoiler * {
    color: var(--G300);
}
.is-spoiler i {
    font-size: 18px;
    cursor: pointer;
}
.is-spoiler.checked i {
    color: var(--FOCUS);
}
.is-spoiler.checked div {
    color: var(--G600);
}
.count-text {
    font-weight: 500;
    font-size: 14px;
    color: var(--FOCUS);
}
.medium-button {
    width: 80px;
}
</style>