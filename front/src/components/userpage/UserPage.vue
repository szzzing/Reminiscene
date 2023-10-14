<template>
    <div class="container">
        <info-component v-bind:user="user" v-bind:category="category"
        @clickCategory="clickCategory"></info-component>
        <wish-component v-if="this.category=='wish'" v-bind:user="user"></wish-component>
        <watching-component v-if="this.category=='watching'" v-bind:user="user"></watching-component>
        <comment-component v-if="this.category=='comment'" v-bind:user="user"></comment-component>
        <like-component v-if="this.category=='like'" v-bind:user="user"></like-component>
        <rate-component v-if="this.category=='rate'" v-bind:user="user"></rate-component>
    </div>
</template>

<script>
import moment from "moment";
import InfoComponent from '../userpage/InfoComponent.vue';
import LikeComponent from "../user/LikeComponent.vue";
import WishComponent from "../user/WishComponent.vue";
import WatchingComponent from '../user/WatchingComponent.vue';
import CommentComponent from "../user/CommentComponent.vue";
import RateComponent from "../user/RateComponent.vue";

export default {
    components: {
    InfoComponent,
    LikeComponent,
    WishComponent,
    WatchingComponent,
    CommentComponent,
    RateComponent,
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