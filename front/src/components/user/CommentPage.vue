<template>
    <div id="wish" v-if="this.user">
        <title-component>
            <template v-slot:emoji>✍️</template>
            <template v-slot:title>
                {{ this.user.nickname ? this.user.nickname : this.user.id }}
                님이<br>작성한 코멘트
            </template>
        </title-component>

        <comment-list-component v-bind:list="list"></comment-list-component>
        <empty-component v-if="this.list.length==0">
            <template v-slot:text>
                작성한 코멘트가 없어요.
            </template>
        </empty-component>
        <infinite-loading @infinite="getList"></infinite-loading>
    </div>
</template>

<script>
import { InfiniteLoading } from 'infinite-loading-vue3-ts';
import TitleComponent from '../item/TitleComponent.vue';
import EmptyComponent from '../item/EmptyComponent.vue';
import CommentListComponent from '../item/CommentListComponent.vue';

export default {
    components: {
        TitleComponent,
        InfiniteLoading,
        EmptyComponent,
        CommentListComponent,
    },

    created() {
        // this.getUser();
    },

    data() {
        return {
            page: 1,
            list: [],
            state: null,
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
            this.axios.get("/comments", {params})
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
            this.axios.get("/comments", {params})
            .then((response)=>{
                const list = response.data.list;
                if(list.length!=0) {
                    this.list.push(...response.data.list);
                    this.page = response.data.page + 1;
                    $state.loaded();
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
.inner {
    display: flex;
    flex-direction: column;
    gap: 24px;
}
</style>