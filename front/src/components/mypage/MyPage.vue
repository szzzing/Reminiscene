<template>
    <div class="container">
        <info-component
        v-bind:category="category"
        @clickCategory="clickCategory"></info-component>

        <wish-component v-bind:user="user" v-if="this.category=='wish'"></wish-component>
        <watching-component v-bind:user="user" v-if="this.category=='watching'"></watching-component>
        <comment-component v-bind:user="user" v-if="this.category=='comment'"></comment-component>
        <like-component v-bind:user="user" v-if="this.category=='like'"></like-component>
        <rate-component v-bind:user="user" v-if="this.category=='rate'"></rate-component>
    </div>
</template>

<script>
import InfoComponent from './InfoComponent.vue'
import WishComponent from '../user/WishComponent.vue';
import WatchingComponent from '../user/WatchingComponent.vue';
import CommentComponent from '../user/CommentComponent.vue';
import LikeComponent from '../user/LikeComponent.vue';
import RateComponent from '../user/RateComponent.vue';

export default {
    components: {
        InfoComponent,
        WishComponent,
        WatchingComponent,
        CommentComponent,
        LikeComponent,
        RateComponent,
    },
    data() {
        return {
            user: this.$store.state.auth.user,
            category: 'wish',
        }
    },
    methods: {
        clickCategory(category) {
            this.category = category;
        },
    },
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