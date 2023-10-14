<template>
    <transition name="smooth">
        <div id="alert" class="item-border" v-if="this.$store.state.modal.alert">
            <div class="emoji">
                {{ this.$store.state.modal.alertEmoji }}
            </div>
            <div class="text">
                {{ this.$store.state.modal.alertText }}
            </div>
        </div>
    </transition>
</template>

<script>
export default {
}
</script>

<style scoped>
#alert {
    position: fixed;
    color: var(--G1000);
    top: 40px;
    left: 50%;
    transform: translateX(-50%);
    z-index: 9999;
    display: flex;
    gap: 8px;
    justify-content: center;
    align-items: center;
    padding: 16px 24px;
    border-radius: 8px;
}
.emoji {
    font-size: 20px;
}
.text {
    word-break: keep-all;
}
</style>