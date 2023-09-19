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
    width: 100%;
    display: flex;
    padding: 0 !important;
    margin: 0 !important;
}
@media screen and (max-width:860px) {
    .container {
        flex-direction: column;
    }
}
</style>