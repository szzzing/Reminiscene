<template>
    <div class="wrap">
        <header-item></header-item>
        <div class="flex-wrap">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
import HeaderItem from './components/common/HeaderItem.vue'

export default {
    components: {
        HeaderItem,
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
    }
}
</script>

<style>

</style>