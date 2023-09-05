<template>
    <div class="container">
        <router-view v-bind:user="user"></router-view>
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
    max-width: 900px;
    display: flex;
    flex-direction: column;
    gap: 72px;
}
</style>