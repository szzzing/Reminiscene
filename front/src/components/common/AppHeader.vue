<template>
   <div class="container">
        <div class="inner">
            <router-link to="/" class="logo">🌙</router-link>
            <router-link to="/login" class="login">로그인</router-link>
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
        width: 100%;
        align-items: center;
        justify-content: space-between;
    }
    .logo {
        font-size: 36px;
    }
    .login {
        height: 32px;
        line-height: 32px;
        padding: 0 16px;
        text-align: center;
        border-radius: 16px;
        color: #181818;
        background: #fff;
        font-size: 14px;
        cursor: pointer;
    }
</style>