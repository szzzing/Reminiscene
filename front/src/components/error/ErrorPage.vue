<template>
    <div class="container">
        <div class="inner">
            <div class="emoji">🚧</div>
            <div class="title">404</div>
            <div class="sub-title">존재하지 않는 페이지입니다.</div>
        </div>
    </div>
</template>

<script>
export default {
}
</script>

<style scoped>
    .container {
        display: flex;
        align-items: center;
        max-width: 1280px;
    }
    .emoji {
        font-size: 60px;
    }
    .title {
        font-size: 36px;
        font-weight: 700;
    }
    .sub-title {
        color: var(--G400);
    }
</style>