<template>
   <div class="container">
        <div class="inner">
            <router-link to="/" class="logo shadow">🌙</router-link>
            <div class="themeBtn shadow"
            @click="this.$store.commit('local/setTheme')">
                {{ this.$store.state.local.theme ? "🌞" : "🌝" }}
            </div>
            <router-link v-if="this.$store.state.auth.user==null"
            to="/login" class="small-button">
                로그인
            </router-link>
            <div v-if="this.$store.state.auth.user!=null"
            @click="clickProfile"
            class="profile-image" :style="{'background-image': 'url(' + user.profileImage + ')' }">
                <div class="no-image" v-if="!user.profileImage">👤</div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    computed: {
        user() {
            return this.$store.state.auth.user;
        }
    },
    methods: {
        clickProfile() {
            const payload = !this.$store.state.modal.profile;
            this.$store.commit("modal/setProfile", payload);
        }
    }
}
</script>

<style scoped>
.container {
    height: 60px;
    position: fixed;
    padding: 20px 0;
    top: 0;
    left: 0;
    right: 0;
    z-index: 9999;
}
.inner {
    display: flex;
    gap: 16px;
    width: 100%;
    align-items: center;
    justify-content: space-between;
}
.logo {
    font-size: 36px;
    margin-right: auto;
}
.themeBtn {
    font-size: 28px;
    cursor: pointer;
}
.profile-image {
    width: 48px;
    height: 48px;
    background-size: cover;
    background-position: center;
    border-radius: 50%;
    background-color: var(--G50);
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
}
.no-image {
    font-size: 28px;
}
</style>