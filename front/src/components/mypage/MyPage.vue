<template>
    <div class="container">
        <router-view></router-view>
    </div>
</template>

<script>
export default {
    data() {
        return {
            user: this.$store.state.auth.user,
        }
    },
}
</script>

<style scoped>
.container {
    max-width: 960px;
}
</style>