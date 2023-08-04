<template>
   <div class="container">
        <div class="inner">
            <router-link to="/" class="logo emoji">🌙</router-link>
            <div class="themeBtn btn emoji"
            @click="this.$store.commit('auth/setTheme')">
                {{ this.$store.state.auth.theme ? "🌞" : "🌝" }}
            </div>
            <router-link to="/login" class="loginBtn">로그인</router-link>
        </div>
    </div>
</template>

<script>
export default {
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
    .loginBtn {
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