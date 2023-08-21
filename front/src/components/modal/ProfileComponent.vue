<template>
    <transition name="smooth">
        <div id="profile-modal" class="item-shadow" v-if="this.$store.state.modal.profile" v-click-outside="closeProfile">
            <div class="inner">
                <div class="profile-image" v-if="user.profileImage" :style="{'background-image': 'url(' + user.profileImage + ')' }"></div>
                <div class="no-image" v-if="!user.profileImage">👤</div>
                <div class="profile-info">
                    <div class="nickname">{{ user.nickname ? user.nickname : user.id }}</div>
                    <div class="id">{{ "@" + user.id }}</div>
                </div>
            </div>
            <div class="inner">
                <router-link to="/mypage" class="small-button">마이페이지</router-link>
                <a class="small-button unset" @click="this.logout()">로그아웃</a>
            </div>
        </div>
    </transition>
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
    padding: 20px;
    border-radius: 16px;
    position: fixed;
    right: 40px;
    top: 80px;
    display: flex;
    flex-direction: column;
    gap: 16px;
}
.dark .item-shadow {
    background: var(--G50);
}
.inner {
    display: flex;
}
.inner:first-child {
    gap: 16px;
    align-items: center;
}
.inner:last-child {
    gap: 8px;
    justify-content: center;
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

.nickname {
    font-size: 18px;
    font-weight: 600;
}
.id {
    color: var(--G500);
}
.small-button.unset {
    color: unset;
    background: unset;
}
.alert-enter-active,
.alert-leave-active {
    transition: all 0.3s linear;
}

.alert-enter-from,
.alert-leave-to {
    opacity: 0;
}
</style>