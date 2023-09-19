<template>
    <div id="wish" v-if="this.user">
        <comment-list-component v-bind:list="list" v-bind:movie="showMovie"></comment-list-component>
        <empty-component v-if="this.list && this.list.length==0">
            <template v-slot:text>
                작성한 코멘트가 없어요.
            </template>
        </empty-component>
        <infinite-loading @infinite="getList"></infinite-loading>
    </div>
</template>

<script>
import { InfiniteLoading } from 'infinite-loading-vue3-ts';
import EmptyComponent from '../item/EmptyComponent.vue';
import CommentListComponent from '../item/CommentListComponent.vue';

export default {
    components: {
        InfiniteLoading,
        EmptyComponent,
        CommentListComponent,
    },
    data() {
        return {
            page: 1,
            list: null,
            state: null,
            showMovie: true,
        }
    },
    props: [
        'user',
    ],
    methods: {
        fetchData() {
            const params = {
                userId: this.$route.params.id,
                page: 1,
                loginUser: this.$store.state.auth.user ? this.$store.state.auth.user.id : null,
            }
            this.axios.get("/comment", {params})
            .then((response)=>{
                this.list = response.data.list;
                this.page = response.data.page + 1;
                this.state.reset();
            });
        },
        getList($state) {
            this.state = $state;

            const params = {
                userId: this.$route.params.id,
                page: this.page,
                loginUser: this.$store.state.auth.user ? this.$store.state.auth.user.id : null,
            }
            this.axios.get("/comment", {params})
            .then((response)=>{
                if(response.data.list.length!=0) {
                    if(this.list) {
                        this.list.push(...response.data.list);
                    } else {
                        this.list = response.data.list;
                    }
                    this.page = response.data.page + 1;
                    $state.loaded();
                } else {
                    if(response.data.page==1) {
                        this.list = [];
                    }
                }
            });
        },
    },
    watch: {
        '$route.params.id': 'fetchData',
    },
}
</script>

<style scoped>
</style>