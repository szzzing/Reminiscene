<template>
    <div class="container">
        <div class="profile-area">
            <div class="profile-image" v-if="this.$store.state.auth.user.profileImage"
            :style="{'background-image': 'url(' + this.$store.state.auth.user.profileImage + ')' }"></div>
            <div class="no-image" v-if="!this.$store.state.auth.user.profileImage">👤</div>
            <div class="nickname">
                {{ this.$store.state.auth.user!=null ? this.$store.state.auth.user.nickname : "찌니" }}
            </div>
            <div class="id">
                {{ this.$store.state.auth.user!=null ? "@"+this.$store.state.auth.user.id : "@szzzing" }}
                <router-link class="modify" to="/mypage/modify">⚙️</router-link>
            </div>
        </div>
    </div>
</template>

<script>
export default {
}
</script>

<style scoped>
.container {
    max-width: 800px;
}
.title {
    font-size: 24px;
    font-weight: 500;
}
.profile-image, .no-image {
    width: 180px;
    height: 180px;
    background-size: cover;
    background-position: center;
    border-radius: 50%;
    background-color: var(--G50);
    display: flex;
    align-items: center;
    justify-content: center;
}
.no-image {
    font-size: 96px;
}

.modify {
    margin-left: 4px;
    font-size: 20px;
}
.nickname {
    font-size: 28px;
    font-weight: 700;
    margin-top: 16px;
}
.id {
    font-size: 20px;
    color: var(--G600);
}
</style>