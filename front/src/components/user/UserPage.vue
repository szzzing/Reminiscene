<template>
    <div class="container">
        <info-component v-bind:user="user" v-bind:category="category"
        @clickCategory="clickCategory"></info-component>
        <wish-page v-if="this.category=='wish'" v-bind:user="user"></wish-page>
        <watching-page v-if="this.category=='watching'" v-bind:user="user"></watching-page>
        <comment-page v-if="this.category=='comment'" v-bind:user="user"></comment-page>
        <like-page v-if="this.category=='like'" v-bind:user="user"></like-page>
    </div>
</template>

<script>
import moment from "moment";
import InfoComponent from '../user/InfoComponent.vue';
import LikePage from "./LikePage.vue";
import WishPage from "./WishPage.vue";
import WatchingPage from '../user/WatchingPage.vue';
import CommentPage from "./CommentPage.vue";

export default {
    components: {
    InfoComponent,
    LikePage,
    WishPage,
    WatchingPage,
    CommentPage,
},

    created() {
        this.fetchUser();
    },
    data() {
        return {
            user: null,
            category: 'wish',
        }
    },
    methods: {
        fetchUser() {
            this.axios.get("/user/"+this.$route.params.id)
            .then((response)=>{
                this.user = response.data;
                if(this.user.birthday) {
                    this.user.birthday = moment(this.user.birthday).format();
                }
            })
            .catch(()=>{
                this.$router.push('/error');
            });
        },
        clickCategory(category) {
            this.category = category;
        }
    },
    watch: {
        '$route.params.id': 'fetchUser',
    }
}
</script>

<style scoped>
.container {
    max-width: 960px;
    display: flex;
    flex-direction: column;
    gap: 72px;
}
</style>