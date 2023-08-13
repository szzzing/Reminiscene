<template>
    <div class="container">

        <div class="profile-area">
            <div class="profile-image"></div>
            <div class="nickname">
                {{ this.$store.state.auth.user!=null ? this.$store.state.auth.user.nickname : "찌니" }}
            </div>
            <div class="id">
                {{ this.$store.state.auth.user!=null ? "@"+this.$store.state.auth.user.id : "@szzzing" }}
                <router-link class="modify" to="/mypage/modify">⚙️</router-link>
            </div>
        </div>

        <div class="review-area">
            <div class="title">내가 쓴 리뷰</div>
        </div>

        <div class="bookmark-area">
            <div class="title">관심있는 영화</div>
        </div>

        <div class="">

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
        display: flex;
        flex-direction: column;
        gap: 48px;
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