<template>
    <div id="empty-list">
        <div class="emoji shadow">🚧</div>
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
    }
    .emoji {
        font-size: 36px;
    }
    .text {
        color: var(--G400);
    }
</style>