<template>
    <div id="header">
        <div class="container">
            <div class="inner">
                <router-link to="/admin" class="logo shadow">🌙</router-link>
                <div class="category-area">
                    <router-link to="/admin/user" class="category">유저관리</router-link>
                    <router-link to="/admin/movie" class="category">영화관리</router-link>
                    <router-link to="/admin" class="category">신고관리</router-link>
                </div>
                
                <div class="themeBtn shadow"
                @click="this.$store.commit('local/setTheme')">
                {{ this.$store.state.local.theme ? "🌞" : "🌝" }}
                </div>
                <div @click="this.logout()" class="small-button">로그아웃</div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            query: '',
        }
    },
    computed: {
        user() {
            return this.$store.state.auth.user;
        }
    },
    methods: {
        logout() {
            this.$store.commit('auth/logout');
        },
    },
}
</script>

<style scoped>
#header {
    background: var(--G0);
    border-bottom: 1px solid var(--G200);
    position: sticky;
    top: 0;
    left: 0;
    right: 0;
    z-index: 9997;
}
.inner {
    display: flex;
    gap: 16px;
    width: 100%;
    align-items: center;
    justify-content: space-between;
}
.logo {
    font-size: 32px;
    margin-right: 16px;
}
.category-area {
    display: flex;
}
.category {
    color: var(--G700);
    padding: 6px 16px;
}
.category:hover {
    background: var(--G50);
    border-radius: 8px;
}
.themeBtn {
    font-size: 28px;
    cursor: pointer;
    margin-left: auto;
}
.profile-image, .no-image {
    width: 36px;
    height: 36px;
}
.no-image {
    font-size: 16px;
}
.search {
    border: 1px solid var(--G200);
    background: var(--G50);
    padding: 4px 12px;
    flex-basis: 200px;
    height: 36px;
    border-radius: 8px;
    display: flex;
    gap: 8px;
    align-items: center;
}
.search i {
    color: var(--G300);
    font-size: 12px;
}
.small-button {
    flex-shrink: 0;
}
</style>