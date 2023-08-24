<template>
    <div class="container">
        <div class="profile-area">
            <div class="profile-image" v-if="this.user.profileImage"
            :style="{'background-image': 'url(' + this.user.profileImage + ')' }"></div>
            <div class="no-image" v-if="!this.user.profileImage">👤</div>
            <div class="info">
                <div class="basic-info">
                    <div class="nickname">
                        {{ this.user.nickname }}
                    </div>
                    <div class="id">
                        {{ "@" + this.user.id }}
                    </div>
                </div>
                <div class="more-info">
                    <div class="birthday">🎂 {{ this.user.birthday ? this.user.birthday.replace(/-/g, '.') : "비공개" }}</div>
                    <div class="gender">⚧️ {{ this.user.gender ? (this.user.gender=='F' ? "여성" : "남성") : "비공개" }}</div>
                </div>
            </div>
            <router-link class="modify" to="/mypage/modify">⚙️</router-link>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            user: this.$store.state.auth.user,
        }
    },
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
.profile-area {
    display: flex;
    flex-direction: column;
    gap: 16px;
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
    font-size: 24px;
    font-weight: 700;
}
.id {
    font-size: 20px;
    color: var(--G500);
}
.info {
    display: flex;
    flex-direction: column;
    gap: 8px;
}
.basic-info,
.more-info {
    display: flex;
    align-items: center;
}
.basic-info {
    gap: 8px;
}
.more-info {
    flex-direction: column;
    align-items: normal;
}
.birthday, .gender {
    color: var(--G400);
}
</style>