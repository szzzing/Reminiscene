<template>
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
</template>

<script>
import InfiniteLoading from 'infinite-loading-vue3-ts';
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

    data() {
        return {
            page: 1,
            list: [],
            user: this.$store.state.auth.user,
        }
    },

    methods: {
        getList($state) {
            const params = {
                userId: this.$store.state.auth.user.id,
                page: this.page,
                loginUser: this.$store.state.auth.user.id,
            }
            this.axios.get("/comment", {params})
            .then((response)=>{
                const list = response.data.list;
                if(list.length!=0) {
                    this.page = response.data.page + 1;
                    this.list.push(...response.data.list);
                    $state.loaded();
                } else {
                    $state.complete();
                }
            });
        },
    },
}
</script>

<style scoped>
.container {
    max-width: 960px;
    display: flex;
    flex-direction: column;
}
</style>