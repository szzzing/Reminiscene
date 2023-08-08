<template>
    <div class="wrap"
    v-bind:class="{ dark : !this.$store.state.local.theme }">
        <header-component></header-component>
        <div class="flex-wrap">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
import HeaderComponent from './components/common/HeaderComponent.vue'

export default {
    components: {
        HeaderComponent,
    },
    data() {
        return {
            movieList: {}
        }
    },
    methods: {
        getList(list) {
            this.movieList = list;
        }
    },
}
</script>

<style>

</style>