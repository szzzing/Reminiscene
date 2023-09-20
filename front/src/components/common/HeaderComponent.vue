<template>
    <div id="header">
        <div class="container">
            <div class="inner">
                <router-link to="/" class="logo shadow">🌙</router-link>
                
                <div class="search" v-if="this.$route.path!='/'">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <input type="text" @click="console.log(this.query)" v-on:keyup.enter="clickSearch" v-model="this.query" @input="this.checkInput($event)" placeholder="영화, 유저를 검색해보세요.">
                </div>
                
                <div class="themeBtn shadow"
                @click="this.$store.commit('local/setTheme')">
                {{ this.$store.state.local.theme ? "🌞" : "🌝" }}
                </div>
                
                <router-link v-if="this.$store.state.auth.user==null" to="/login" class="small-button">로그인</router-link>
                
                <div v-if="user && user.profileImage" @click="clickProfile"
                class="profile-image" :style="{'background-image': 'url(' + user.profileImage + ')' }"></div>
                <div class="no-image" v-if="user && !user.profileImage" @click="clickProfile"><i class="fa-solid fa-user"></i></div>
            </div>
        </div>
        <search-suggest-component v-if="this.$route.path!='/' && this.query.trim()!=''"
        v-bind:query="query">
        </search-suggest-component>
    </div>
</template>

<script>
import SearchSuggestComponent from './SearchSuggestComponent.vue';

export default {
    components: {
        SearchSuggestComponent,
    },
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
        clickProfile() {
            const payload = !this.$store.state.modal.profile;
            this.$store.commit("modal/setProfile", payload);
        },
        clickSearch() {
            if(this.query.trim()!='') {
                this.$router.push("/search/"+this.query.trim());
                this.query = '';
            }
        },
        checkInput(event) {
            this.query = event.target.value;
        },
        fetchData() {
            console.log("이동")
            this.query = '';
        },
    },
    watch: {
        '$route.path': 'fetchData',
    },
}
</script>

<style scoped>
#header {
    background: var(--G0);
    border-bottom: 1px solid var(--G100);
    position: sticky;
    top: 0;
    left: 0;
    right: 0;
    width: 100%;
    height: 52px;
    z-index: 9997;
}
.inner {
    display: flex;
    gap: 16px;
    align-items: center;
    justify-content: space-between;
}
.logo {
    font-size: 32px;
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
    background: var(--G50);
    padding: 4px 12px;
    flex-basis: 280px;
    height: 36px;
    border-radius: 8px;
    display: flex;
    gap: 8px;
    margin-right: auto;
    align-items: center;
}
.search input {
    font-size: 16px;
}
.search i {
    color: var(--G300);
    font-size: 12px;
}
.small-button {
    flex-shrink: 0;
}
</style>