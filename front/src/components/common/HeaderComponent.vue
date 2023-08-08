<template>
   <div class="container">
        <div class="inner">
            <router-link to="/" class="logo shadow">🌙</router-link>
            <div class="themeBtn shadow"
            @click="this.$store.commit('local/setTheme')">
                {{ this.$store.state.local.theme ? "🌞" : "🌝" }}
            </div>
            <router-link v-if="this.$store.state.auth.user==null"
            to="/login" class="btn">
                로그인
            </router-link>
            <a v-if="this.$store.state.auth.user!=null"
            class="btn"
            @click="this.logout()">
                로그아웃
            </a>
        </div>
    </div>
</template>

<script>
export default {
    methods: {
        logout() {
            this.$store.commit('auth/logout');
        }
    }
}
</script>

<style scoped>
    .container {
        height: 60px;
        position: fixed;
        padding: 20px 0;
        top: 0;
        left: 0;
        right: 0;
        z-index: 9999;
    }
    .inner {
        display: flex;
        gap: 16px;
        width: 100%;
        align-items: center;
        justify-content: space-between;
    }
    .logo {
        font-size: 36px;
        margin-right: auto;
    }
    .btn {
        height: 32px;
        line-height: 32px;
        padding: 0 16px;
        text-align: center;
        border-radius: 16px;
        color: var(--G0);
        background: var(--G1000);
        font-size: 14px;
        cursor: pointer;
    }
    .themeBtn {
        font-size: 28px;
        cursor: pointer;
    }
</style>