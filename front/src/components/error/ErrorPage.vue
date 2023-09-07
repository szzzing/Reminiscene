<template>
    <div class="container">
        <div class="shadow emoji">⛔️</div>
        <div class="title">페이지가<br>존재하지 않아요
        </div>
        <div class="sub-title">
            <router-link to="/">메인 페이지로 이동할게요</router-link>
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
    flex-direction: column;
    max-width: 800px;
}
.emoji {
    font-size: 60px;
}
.title {
    font-size: 30px;
    font-weight: 700;
    margin-bottom: 16px;
    line-height: 1.4;
}
.sub-title {
    color: var(--G400);
    font-size: 18px;
}
</style>