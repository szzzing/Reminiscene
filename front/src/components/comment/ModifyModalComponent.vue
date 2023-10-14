<template>
    <transition name="smooth">
        <div id="modify-modal" class="modal-mask" @click="$emit('closeModifyModal')">
            <div class="modal-wrapper">
                <div class="modal-container item-fill" @click.stop="">
                    <div class="inner">
                        <div class="title">수정</div>
                        <i class="close fa-solid fa-circle-xmark" @click="$emit('closeModifyModal')"></i>
                    </div>
                    <div class="inner">
                        <textarea class="text" v-model="this.text" @input="this.count($event)" placeholder="이 코멘트에 대한 생각을 자유롭게 표현해주세요." maxlength="1000"></textarea>
                    </div>
                    <div class="inner">
                        <div class="count-text">{{ this.textCount }}/1000</div>
                        <div class="medium-button" @click="this.modifyReply()" :class="{'disabled': this.textCount==0 || this.textCount>1000 }">저장</div>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>

<script>
export default {
    data() {
        return {
            text: this.reply.content,
            textCount: this.reply.content.length,
        }
    },
    props: [
        'reply',
    ],
    watch: {
        textCount() {
            if(this.textCount>=1000) {
                this.$store.commit("modal/setAlert", { alertEmoji: "⚠️", alertText: "최대 글자수를 채웠어요." });
            }
        }
    },
    methods: {
        count(event) {
            this.textCount = event.target.value.length;
        },
        modifyReply() {
            const params = {
                id: this.reply.id,
                content: this.text,
            };
            this.axios.put("/reply", params)
            .then(()=>{
                this.$emit('updateReply', params);
                this.$emit('closeModifyModal');
                this.$store.commit("modal/setAlert", { alertEmoji: "😃", alertText: "댓글을 수정했어요." });
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