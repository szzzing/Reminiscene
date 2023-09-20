<template>
    <div id="header">
        <div class="container">
            <div class="inner">
                <router-link to="/" class="logo shadow">🌙</router-link>
                
                <div class="search" v-if="this.$route.path!='/'">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <input type="text" ref="query" maxlength="40"
                    @keyup.enter="clickSearch"
                    @input="this.checkInput($event)"
                    placeholder="영화, 유저를 검색해보세요.">
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
        <div id="search-suggest" v-if="this.list.length!=0 && this.query.trim()!=''">
            <div class="container">
                <router-link class="item" v-for="(movie) in this.list" :key="movie" :to="`/detail/${movie.id}`">
                    <div class="title">{{ movie.title }}</div>
                    <div class="release-date">{{ movie.releaseDate.substring(0, 4) }}</div>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            query: '',
            list: [],
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
            console.log("check")
            this.query = event.target.value.trim().replaceAll("/", "");
            if(this.query!='') {
                this.axios.get(`/movie/search/${this.query}`)
                .then((response)=>{
                    this.list = response.data;
                })
            } else {
                this.list = [];
            }
        },
        fetchData() {
            this.query = '';
            if(this.$refs.query) {
                this.$refs.query.value = '';
            }
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
#search-suggest {
    background: var(--BLUR);
    width: 100%;
    backdrop-filter: blur(16px);
    position: relative;
    top: 0;
    right: 0;
    left: 0;
}
#search-suggest .container {
    padding: 16px 0;
}
#search-suggest .item {
    display: flex;
    align-items: center;
    gap: 6px;
}
#search-suggest .title {
    font-weight: 600;
    color: var(--G700);
}
#search-suggest .release-date {
    color: var(--O600);
    font-size: 14px;
}
</style>