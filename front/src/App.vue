<template>
    <admin-header-component v-if="this.$store.state.auth.user && this.$store.state.auth.user.role=='A'"></admin-header-component>
    <header-component v-else></header-component>

    <profile-component></profile-component>
    <alert-component></alert-component>

    <div class="wrap" @click="this.closeProfile(); this.closeSuggest();">
        <router-view></router-view>
    </div>

    <footer-component></footer-component>
</template>

<script>
import HeaderComponent from './components/common/HeaderComponent.vue'
import AlertComponent from './components/modal/AlertComponent.vue';
import FooterComponent from './components/common/FooterComponent.vue';
import ProfileComponent from './components/modal/ProfileComponent.vue';
import AdminHeaderComponent from './components/common/AdminHeaderComponent.vue';

export default {
    components: {
        HeaderComponent,
        AlertComponent,
        FooterComponent,
        ProfileComponent,
        AdminHeaderComponent,
    },
    data() {
        return {
            movieList: {}
        }
    },
    methods: {
        getList(list) {
            this.movieList = list;
        },
        closeProfile() {
            this.$store.commit("modal/setProfile", false);
        },
        closeSuggest() {
            this.$store.commit("local/setSuggest", false);
        },
    },
    // 테마 상태 반영
    created() {
        if(!this.$store.state.local.theme) {
            document.documentElement.classList.add('dark');
        }
    },
}
</script>

<style>
    #app {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        min-height: 100vh;
    }
</style>