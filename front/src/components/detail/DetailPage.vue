<template>
    <div class="container">
        <detail-component></detail-component>
        <review-component></review-component>
    </div>
</template>

<script>
import DetailComponent from './DetailComponent.vue'
import ReviewComponent from './ReviewComponent.vue'

export default {
    components: { 
        DetailComponent,
        ReviewComponent,
    },
}
</script>

<style scoped>
.flex-wrap>.container:first-child {
    min-height: calc(100vh);
    padding: 0 0 100px;
    width: 100%;
}
</style>