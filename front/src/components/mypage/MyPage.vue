<template>
    <div class="container">
        <info-component
        v-bind:category="category"
        @clickCategory="clickCategory"></info-component>

        <wish-page v-if="this.category=='wish'"></wish-page>
        <watching-page v-if="this.category=='watching'"></watching-page>
        <comment-page v-if="this.category=='comment'"></comment-page>
        <like-page v-if="this.category=='like'"></like-page>
    </div>
</template>

<script>
import CommentPage from './CommentPage.vue';
import InfoComponent from './InfoComponent.vue'
import LikePage from './LikePage.vue';
import WatchingPage from './WatchingPage.vue';
import WishPage from './WishPage.vue';
export default {
    components: {
        InfoComponent,
        WishPage,
        WatchingPage,
        CommentPage,
        LikePage,
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