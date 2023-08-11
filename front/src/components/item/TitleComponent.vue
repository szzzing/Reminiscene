<template>
    <div id="title">
        <div class="emoji shadow">
            <router-link to="/">
                <slot name="emoji"></slot>
            </router-link>
        </div>
        <div class="title">
            <slot name="title"></slot>
        </div>
    </div>
</template>

<script>
export default {
}
</script>

<style scoped>
    #title {
        max-width: 200px;
        margin: 0 0 72px;
    }
    .emoji {
        margin: 0 0 12px;
        font-size: 40px;
        font-weight: 900;
        color: var(--G0);
    }
    .title {
        color: var(--G700);
        font-size: 28px;
        font-weight: 600;
    }
</style>