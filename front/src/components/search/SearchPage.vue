<template>
    <div class="container">
        <title-component>
            <template v-slot:emoji>🤔</template>
            <template v-slot:title>
                "{{ this.query }}"의 검색결과
            </template>
        </title-component>
        <router-view v-bind:query="query" :key="$route.params.query"></router-view>
    </div>
</template>

<script>
import TitleComponent from '../item/TitleComponent.vue';

export default {
    components: {
        TitleComponent,
    },
    updated() {
        this.query = this.$route.params.query;
    },
    data() {
        return {
            query: this.$route.params.query,
        }
    },
}
</script>

<style scoped>
.container {
    max-width: 1280px;
    display: flex;
    flex-direction: column;
}
.query {
    font-size: 18px;
}
</style>