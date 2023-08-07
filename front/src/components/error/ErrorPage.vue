<template>
    <div class="container">
        <div class="inner">
            <title-item>
                <template v-slot:title>404</template>
            </title-item>
            <div class="content">존재하지 않는 페이지입니다.</div>
        </div>
    </div>
</template>

<script>
import TitleItem from '../item/TitleItem.vue'

export default {
    components: {
        TitleItem,
    },
}
</script>

<style scoped>
    .container {
        display: flex;
        align-items: center;
        max-width: 600px;
    }
    #title {
        margin: 0 0 20px;
    }
    .content {
        color: var(--G400);
    }
</style>