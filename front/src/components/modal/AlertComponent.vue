<template>
    <transition name="alert">
        <div id="alert" v-if="this.$store.state.modal.alert">
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

<style>
#alert {
    position: fixed;
    background: var(--G0);
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
    filter: drop-shadow(0px 0px 10px var(--O100));
}
.dark #alert {
    background: var(--G50);
}
.emoji {
    font-size: 20px;
}
.alert-enter-active,
.alert-leave-active {
    transition: all 0.3s linear;
}

.alert-enter-from,
.alert-leave-to {
    opacity: 0;
}
</style>