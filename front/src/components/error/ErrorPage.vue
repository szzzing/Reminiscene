<template>
    <div class="container">
        <div class="inner">
            <div class="emoji">🚧</div>
            <div class="title">
                페이지가<br>
                존재하지 않아요
            </div>
            <div class="sub-title">
                <router-link>메인 페이지로 이동할게요</router-link>
            </div>
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