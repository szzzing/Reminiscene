<template>
    <div id="wish" class="container">
        <list-component v-bind:list="list"></list-component>
        <infinite-loading @infinite="getList"></infinite-loading>
    </div>
</template>

<script>
import ListComponent from '../list/ListComponent.vue';
import InfiniteLoading from 'infinite-loading-vue3-ts';
export default {
    components: {
        ListComponent,
        InfiniteLoading,
    },

    data() {
        return {
            list: [],
            page: 1,
        }
    },

    methods: {
        getList($state) {
            console.log("list");
            $state.complete();
            const params = {
                userId: this.$store.state.auth.user.id,
                page: this.page,
            }
            this.axios.get("/comments", {params})
            .then((response)=>{
                console.log(response.data)
            })
        }
    }
}
</script>

<style>

</style>