<template>
    <div id="logo">
        <img class="emoji" src="../../../public/logo.svg">
        <div class="text">어떤 영화를 보여줄까?</div>
    </div>
</template>

<script>
export default {

}
</script>

<style scoped>
#logo {
    text-align: center;
}
.emoji {
    width: 48px;
    line-height: 1.2;
}
.text {
    color: var(--G500);
    font-weight: 600;
}
</style>