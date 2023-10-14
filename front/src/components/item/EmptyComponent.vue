<template>
    <div id="empty-list">
        <div class="emoji shadow">😶‍🌫️</div>
        <div class="text">
            <slot name="text"></slot>
        </div>
    </div>
</template>

<script>
export default {
}
</script>

<style scoped>
    #empty-list {
        text-align: center;
        background: var(--G50);
        padding: 20px;
        border-radius: 8px;
    }
    .emoji {
        font-size: 24px;
    }
    .text {
        color: var(--G400);
    }
</style>