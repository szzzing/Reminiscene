<template>
    <div id="title">
        <div class="title emoji">
            <router-link to="/">
                🌙
            </router-link>
        </div>
        <div class="sub-title">
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
        margin: 0 0 60px;
    }
    .title {
        margin: 0 0 12px;
        font-size: 40px;
        font-weight: 900;
        color: var(--G0);
    }
    .sub-title {
        color: var(--G700);
        font-size: 24px;
        font-weight: 600;
    }
</style>