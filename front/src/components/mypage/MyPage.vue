<template>
    <div class="container">
        <info-component
        v-bind:category="category"
        @clickCategory="clickCategory"></info-component>

        <wish-component v-if="this.category=='wish'"></wish-component>
        <watching-component v-if="this.category=='watching'"></watching-component>
        <comment-component v-if="this.category=='comment'"></comment-component>
        <like-component v-if="this.category=='like'"></like-component>
    </div>
</template>

<script>
import InfoComponent from './InfoComponent.vue'
import WishComponent from './WishComponent.vue';
import WatchingComponent from './WatchingComponent.vue';
import CommentComponent from './CommentComponent.vue';
import LikeComponent from './LikeComponent.vue';
export default {
    components: {
        InfoComponent,
        WishComponent,
        WatchingComponent,
        CommentComponent,
        LikeComponent,
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