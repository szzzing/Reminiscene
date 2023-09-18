<template>
    <div class="container">
        <router-view></router-view>
    </div>
</template>
 
<script>
export default {
}
</script>

<style scoped>
.container {
    max-width: 1280px;
    display: flex;
    flex-direction: column;
    gap: 72px;
}
</style>