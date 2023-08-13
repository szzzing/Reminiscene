<template>
    <div class="container">
        <info-component></info-component>
        <router-view></router-view>
    </div>
</template>

<script>
import InfoComponent from './InfoComponent.vue';

export default {
    components: {
        InfoComponent,
    },
}
</script>

<style scoped>
</style>