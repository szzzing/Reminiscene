<template>
    <title-component>
        <template v-slot:emoji>🔎</template>
        <template v-slot:title>내 정보 찾기</template>
    </title-component>
    <div id="find">
        <router-link to="/auth/find/id" class="item">
            <div class="title">아이디 찾기</div>
        </router-link>
        <router-link to="/auth/find/pw" class="item">
            <div class="title">비밀번호 찾기</div>
        </router-link>
    </div>
    </template>

<script>
import TitleComponent from '../item/TitleComponent.vue'
export default {
    components: {
        TitleComponent
    },
}
</script>

<style scoped>
#find {
    display: flex;
    flex-direction: column;
    gap: 20px;
}
.item {
    border-radius: 8px;
    padding: 16px;
    background: var(--G50);
    border: 1px solid var(--G100);
}
.title {
    font-size: 18px;
    text-align: center;
    color: var(--G700);
    font-weight: 500;
}
.emoji {
    font-size: 40px;
    position: absolute;
    top: -32px;
}
</style>