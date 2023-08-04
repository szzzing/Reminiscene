<template>
    <div>
        <div class="backdrop" :style="{'background-image': 'url('+this.$store.state.details.backdrop_path+')' }">
            <div class="container">
                <div class="title emoji">
                    {{ this.$store.state.details.title }}
                </div>
                <div class="original-title emoji" v-if="this.$store.state.details.title !=this.$store.state.details.original_title">
                    {{ this.$store.state.details.original_title }}
                </div>
                <div class="genre-release emoji"
                v-if="this.$store.state.details.genres+this.$store.state.details.release_date!=''">
                    {{ [this.$store.state.details.genres,this.$store.state.details.release_date].join(" ・ ") }}
                </div>
                <div class="runtime emoji"
                v-if="this.$store.state.details.runtime!=0">
                    {{ this.$store.state.details.runtime+'분' }}
                </div>
            </div>
        </div>
        <div class="container"
            v-if="this.$store.state.details.overview!=''">
            <div class="inner">
                <div class="tagline"
                v-if="this.$store.state.details.tagline!=''">
                    {{ this.$store.state.details.tagline }}
                </div>
                <div class="overview">
                    {{ this.$store.state.details.overview }}
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    beforeCreate() {
        console.log(this.$route.params.id);
        this.$store.dispatch('searchMovie', this.$route.params.id);
        console.log(this.$route.params.id);
    },
    computed: {
        movie() {
            return this.$store.state.movie
        }
    },
}
</script>

<style scoped>
    .backdrop {
        height: 100vh;
        background-size: cover;
        background-position: center;
        height: 70vh;
        position: relative;
    }
    .backdrop * {
        color: #fff;
    }
    .backdrop > .container {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
    }
    .container {
        padding: 40px 0;
    }
    .title {
        font-size: 48px;
        font-weight: 700;
    }
    .original-title {
        font-size: 24px;
        font-weight: 400;
    }
    .genre-release {
        margin-top: 8px;
    }
    .runtime {
        margin-top: 4px;
    }
    .genre-release, .runtime {
        font-size: 18px;
        font-weight: 500;
    }
    .tagline {
        font-size: 24px;
        font-weight: 600;
        margin-bottom: 12px;
    }
    .overview {
        font-size: 18px;
        line-height: 1.4;
    }
</style>