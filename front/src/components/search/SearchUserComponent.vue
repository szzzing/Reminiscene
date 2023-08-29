<template>
    <transition-group id="profile-list" tag="div" class="list">
        <router-link class="item" v-for="(user) in this.userList" :key="user" :to="`/user/${user.id}`">
            <div class="image">
                <div class="profile-image" v-if="user.profileImage" :style="{'background-image': `url(${user.profileImage}` }">
                </div>
                <div class="no-image" v-if="!user.profileImage">👤</div>
            </div>
            <div class="info">
                <div class="nickname">{{ user.nickname!=null ? user.nickname : user.id }}</div>
                <div class="id">{{ "@"+user.id }}</div>
                <div class="commentCount">{{ user.commentCount+"개의 평가" }}</div>
            </div>
        </router-link>
    </transition-group>

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

export default {
    components: {
        EmptyComponent,
        InfiniteLoading,
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
                console.log(response.data, this.page);
                if(response.data.list.length>0) {
                    this.userList.push(...response.data.list);
                    this.page = response.data.page+1;
                    $state.loaded();
                }
            })
        },
    },

    // /detail 페이지로 이동시 검색결과 유지
    // 그 외 페이지로 이동시 검색결과 유지 x
    beforeRouteLeave(to, from, next) {
        if(!to.path.startsWith('/detail')) {
            this.$store.commit('movie/setQuery', '');
            this.$store.commit('movie/setList', []);
            this.$store.commit('movie/setPage', 1);
            this.$store.commit('movie/setState', null);
        } else {
            this.$store.commit('movie/setQuery', this.query);
            this.$store.commit('movie/setList', this.list);
            this.$store.commit('movie/setPage', this.page);
            this.$store.commit('movie/setState', this.state);
        }
        next();
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