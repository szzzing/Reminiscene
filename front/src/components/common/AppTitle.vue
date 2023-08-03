<template>
    <div id="title">
        <router-link to="/">
            <div class="title emoji">
                🌙
            </div>
            <div class="sub-title">어떤 영화가 궁금해?</div>
        </router-link>
    </div>
</template>

<script>
export default {
}
</script>

<style scoped>
    #title {
        max-width: 200px;
        margin: 60px auto;
    }
    .title {
        margin: 0 0 12px;
        font-size: 40px;
        font-weight: 900;
        text-align: center;
        color: var(--G0);
    }
    .sub-title {
        color: var(--G400);
        text-align: center;
        font-size: 18px;
        font-weight: 700;
    }
</style>