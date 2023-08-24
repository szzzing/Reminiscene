<template>
    <transition name="smooth">
        <div id="delete-modal" class="modal-mask" @click="$emit('closeDeleteModal')">
            <div class="modal-wrapper">
                <div class="modal-container item-shadow" @click.stop="">
                    <div class="inner">
                        <div class="emoji shadow">🥺</div>
                        <div class="text">댓글을 삭제할까요?</div>
                    </div>
                    <div class="inner">
                        <div class="button" @click="this.deleteReply()">네</div>
                        <div class="button" @click="$emit('closeDeleteModal')">아니요</div>
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
        }
    },
    props: [
        'reply',
    ],
    methods: {
        deleteReply() {
            this.axios.delete("/reply/"+this.reply.id)
            .then((response)=>{
                console.log(response);
                this.$emit('removeReply', this.reply.id);
                this.$emit('closeDeleteModal');
                this.$store.commit("modal/setAlert", { alertEmoji: "😃", alertText: "댓글을 삭제했어요." });
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
    /* backdrop-filter: blur(10px); */
    background: var(--O300);
    display: table;
}
.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}
.modal-container {
    max-width: 300px;
    margin: 0 auto;
    border-radius: 16px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    gap: 16px;
    text-align: center;
}
.inner {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
}
.inner:first-child {
    flex-direction: column;
    padding: 20px 20px 0;
}
.inner:last-child {
    border-top: 1px solid var(--G100);
}
.emoji {
    font-size: 36px;
}
.button {
    width: 50%;
    padding: 12px 20px;
    cursor: pointer;
}
.button:first-child {
    border-right: 1px solid var(--G100);
}
</style>