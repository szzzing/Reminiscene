<template>
    <div id="content">
        <div class="backdrop" :style="{'background-image': 'url('+this.$store.state.details.backdrop_path+')' }">
            <div class="area">
                <div class="main inner">
                    <div class="title">
                        {{ this.$store.state.details.title }}
                    </div>
                    <div class="original-title" v-if="this.$store.state.details.title !=this.$store.state.details.original_title">
                        {{ this.$store.state.details.original_title }}
                    </div>
                    <div class="genre-release">
                        {{ [this.$store.state.details.genres,this.$store.state.details.release_date].join(" ・ ") }}
                    </div>
                    <div class="runtime">
                        {{ this.$store.state.details.runtime+'분' }}
                    </div>
                </div>
                <div class="info"
                    v-if="this.$store.state.details.overview!=''">
                    <div class="inner">
                        <div class="tagline">
                            {{ this.$store.state.details.tagline }}
                        </div>
                        <div class="overview">
                            {{ this.$store.state.details.overview }}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    beforeCreate() {
        this.$store.dispatch('getDetails', this.$route.params.id);
    }
}
</script>

<style scoped>
    #content {
        margin: 0 !important;
    }
    .backdrop {
        padding: 60px;
        height: 100vh;
        background-size: cover;
        background-position: center;
        position: relative;
    }
    .area {
        position: absolute;
        left: 0;
        right: 0;
        bottom: 0;
    }
    .main {
        margin-bottom: 40px;
    }
    .info {
        backdrop-filter: blur(10px);
        padding: 40px 0 60px;
        background: rgba(0,0,0,0.5);
    }
    
    .title {
        font-size: 48px;
        font-weight: 700;
        filter: drop-shadow(0px 0px 6px rgba(0,0,0,0.2));
    }
    .original-title {
        font-size: 28px;
        font-weight: 400;
        filter: drop-shadow(0px 0px 6px rgba(0,0,0,0.2));
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
        color: #fff;
        font-size: 24px;
        font-weight: 600;
        margin-bottom: 12px;
    }
    .overview {
        color: #fff;
        font-size: 18px;
        line-height: 1.4;
    }
</style>