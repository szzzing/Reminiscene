<template>
    <transition name="smooth">
        <div id="alert" class="item-shadow" v-if="this.$store.state.modal.alert">
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
    padding: 20px 24px;
    border-radius: 16px;
}
.dark .item-shadow {
    background: var(--G50);
}
.emoji {
    font-size: 20px;
}
</style>