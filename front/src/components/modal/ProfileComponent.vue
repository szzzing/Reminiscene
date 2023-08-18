<template>
    <transition name="smooth">
        <div id="profile-modal" v-if="this.$store.state.modal.profile">
            <div class="inner">
                <div class="profile-image" :style="{'background-image': 'url(' + user.profileImage + ')' }">
                    <div class="no-image" v-if="!user.profileImage">👤</div>
                </div>
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
        }
    },
}
</script>

<style scoped>
#profile-modal {
    background: var(--G0);
    padding: 20px;
    border-radius: 16px;
    position: fixed;
    right: 40px;
    top: 80px;
    display: flex;
    flex-direction: column;
    gap: 16px;
    filter: drop-shadow(0px 0px 6px var(--O100));
}
.dark #profile-modal {
    background: var(--G50);
    filter: none;
}
@media screen and (max-width:768px) {
    #profile-modal {
        right: 20px;
        display: flex;
        flex-direction: column;
        gap: 16px;
    }
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
.profile-image {
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