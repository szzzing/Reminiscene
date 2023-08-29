<template>
    <div class="container">
        <div class="query">
            <b>{{ this.query }}</b>의 검색결과
        </div>
        <transition-group id="profile-list" tag="div" class="list">
            <router-link class="item" v-for="(user) in this.userList" :key="user" :to="`/user/${user.id}`">
                <div class="image">
                    <div class="profile-image" v-if="user.profileImage" :style="{'background-image': `url(${user.profileImage}` }">
                    </div>
                    <div class="no-image" v-if="!user.profileImage">👤</div>
                </div>
                <div class="info">
                    <div class="nickname">{{ user.nickname!='' ? user.nickname : user.id }}</div>
                    <div class="id">{{ "@"+user.id }}</div>
                    <div class="commentCount">{{ user.commentCount+"개의 평가" }}</div>
                </div>
            </router-link>
        </transition-group>
    </div>
</template>

<script>
export default {

    data() {
        return {
            query: this.$route.params.query,
            userList: [],
        }
    },
    created() {
        this.getUserList();
    },
    methods: {
        // 유저 리스트 불러오기
        getUserList() {
            const params = {
                query: this.query,
                page: 1,
            }
            this.axios.get("/user", {params})
            .then((response)=>{
                this.userList = response.data.list;
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
.container {
    max-width: 1280px;
    display: flex;
    flex-direction: column;
    gap: 60px;
}
.query {
    font-size: 18px;
}
#profile-list {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
}
#profile-list .item {
    border-bottom: 1px solid var(--G200);
    width: calc((100% - 40px)/3);
    padding-bottom: 20px;
    display: flex;
    gap: 16px;
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