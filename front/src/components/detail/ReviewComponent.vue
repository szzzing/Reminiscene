<template>
    <div class="container">
    </div>
</template>

<script>
export default {
    computed: {
        user() {
            return this.$store.state.auth.user;
        }
    },
    watch: {
        user() {
            console.log("로그아웃 감지");
        }
    }
}
</script>

<style scoped>
    .container {
        padding: 60px 0 0;
        max-width: 1280px;
    }
</style>