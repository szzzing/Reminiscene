<template>
    <user-list-component v-bind:list="userList"></user-list-component>

    <empty-component v-if="this.userList.length==0">
        <template v-slot:text>
            검색결과가 없어요.
        </template>
    </empty-component>

    <infinite-loading @infinite="getUserList"></infinite-loading>
</template>

<script>
import EmptyComponent from '../item/EmptyComponent.vue';
import { InfiniteLoading } from 'infinite-loading-vue3-ts';
import UserListComponent from '../item/UserListComponent.vue';

export default {
    components: {
        EmptyComponent,
        InfiniteLoading,
        UserListComponent,
    },
    data() {
        return {
            userList: [],
            page: 1,
        }
    },
    props: [
        'query',
    ],
    methods: {
        // 유저 리스트 불러오기
        getUserList($state) {
            const params = {
                query: this.query,
                page: this.page,
            }
            this.axios.get("/user", {params})
            .then((response)=>{
                if(response.data.list.length>0) {
                    this.userList.push(...response.data.list);
                    this.page = response.data.page+1;
                    $state.loaded();
                }
            })
        },
    },
}
</script>

<style scoped>
.query {
    font-size: 18px;
}
#profile-list {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
}
#profile-list .item {
    border-bottom: 1px solid var(--G100);
    width: calc((100% - 40px)/3);
    padding-bottom: 20px;
    display: flex;
    gap: 16px;
}
@media screen and (max-width:768px) {
    #profile-list .item {
        width: calc((100% - 20px)/2);
    }
}
@media screen and (max-width:474px) {
    #profile-list .item {
        width: 100%;
    }
}
.profile-image, .no-image {
    width: 72px;
    height: 72px;
    font-size: 36px;
}
#profile-list .info {
    display: flex;
    flex-direction: column;
}
.id {
    color: var(--G400);
    flex-grow: 1;
    line-height: 1;
}
.nickname {
    font-weight: 600;
    line-height: 1.4;
}
.commentCount {
    color: var(--G500);
    font-size: 14px;
}
</style>