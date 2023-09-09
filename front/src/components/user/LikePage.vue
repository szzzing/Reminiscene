<template>
    <div id="wish" v-if="this.user">
        <title-component>
            <template v-slot:emoji>💛</template>
            <template v-slot:title>
                {{ this.user.nickname ? this.user.nickname : this.user.id }}
                님이<br>좋아하는 코멘트
            </template>
        </title-component>

        <comment-list-component v-bind:list="list"></comment-list-component>
        <empty-component v-if="this.list.length==0">
            <template v-slot:text>
                좋아하는 코멘트가 없어요.
            </template>
        </empty-component>
        
        <infinite-loading @infinite="getList"></infinite-loading>
    </div>
</template>

<script>
import InfiniteLoading from 'infinite-loading-vue3-ts';
import TitleComponent from '../item/TitleComponent.vue';
import EmptyComponent from '../item/EmptyComponent.vue';
import CommentListComponent from '../item/CommentListComponent.vue';
import moment from "moment";

export default {
    components: {
        TitleComponent,
        InfiniteLoading,
        EmptyComponent,
        CommentListComponent,
    },
    created() {
        this.fetchUser();
    },
    data() {
        return {
            page: 1,
            list: [],
            user: null,
        }
    },
    methods: {
        fetchUser() {
            this.axios.get("/user/"+this.$route.params.id)
            .then((response)=>{
                this.user = response.data;
                this.user.birthday = moment(this.user.birthday).format();
            })
            .catch(()=>{
                this.$router.push('/error');
            });
        },
        getList($state) {
            const params = {
                userId: this.user.id,
                loginUser: this.$store.state.auth.user ? this.$store.state.auth.user.id : null,
                page: this.page,
            }
            this.axios.get("/like", {params})
            .then((response)=>{
                if(response.data.list.length!=0) {
                    this.page = response.data.page + 1;
                    this.list.push(...response.data.list);
                    $state.loaded();
                } else {
                    $state.complete();
                }
            })
        },
    },
    watch: {
        '$route.params.id': 'fetchUser',
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