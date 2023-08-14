<template>
    <div class="container">
        <div class="profile-area">
            <div class="profile-image">
            </div>
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
.profile-image {
    background-image: url("../../../public/profile.png");
    width: 180px;
    height: 180px;
    background-size: cover;
    background-position: center;
    border-radius: 50%;
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