<template>
    <div id="profile-modal" class="item-border" v-if="this.$store.state.modal.profile" @click.stop="">
        <div class="inner">
            <div class="profile-image" v-if="user.profileImage" :style="{'background-image': 'url(' + user.profileImage + ')' }"></div>
            <div class="no-image" v-if="!user.profileImage">👤</div>
            <div class="profile-info">
                <div class="nickname">{{ user.nickname ? user.nickname : user.id }}</div>
                <div class="id">{{ "@" + user.id }}</div>
                <div class="activity">{{ user.commentCount+"개의 코멘트" }}</div>
            </div>
        </div>
        <div class="inner">
            <router-link to="/mypage">마이페이지</router-link>
            <div @click="this.logout()">로그아웃</div>
        </div>
    </div>
</template>

<script>
export default {
    computed: {
        user() {
            return this.$store.state.auth.user;
        },
    },
    methods: {
        logout() {
            this.$store.commit('auth/logout');
        },
        closeProfile() {
            console.log("누름");
            this.$store.commit("modal/setProfile", false);
        }
    },
}
</script>

<style scoped>
#profile-modal {
    z-index: 9999;
    border-radius: 16px;
    position: fixed;
    right: 40px;
    top: 80px;
    display: flex;
    flex-direction: column;
    gap: 16px;
}
.inner {
    display: flex;
    justify-content: center;
}
.inner:first-child {
    gap: 16px;
    padding: 20px 20px 0;
}
.inner:last-child {
    border-top: 1px solid var(--G100);
}
.inner:last-child > *:first-child {
    border-right: 1px solid var(--G100);
}
.inner:last-child > * {
    padding: 12px 20px;
    font-size: 14px;
    flex-grow: 1;
    text-align: center;
    cursor: pointer;
}

.profile-image, .no-image {
    width: 80px;
    height: 80px;
    background-size: cover;
    background-position: center;
    border-radius: 50%;
    background-color: var(--G50);
    display: flex;
    align-items: center;
    justify-content: center;
}
.no-image {
    font-size: 48px;
}

.profile-info {
    min-width: 80px;
    display: flex;
    flex-direction: column;
}
.nickname {
    font-weight: 600;
    line-height: 1.2;
}
.id {
    font-size: 14px;
    color: var(--G400);
    line-height: 1.2;
    flex-grow: 1;
}
.activity {
    font-size: 14px;
    color: var(--G500);
}
</style>