<template>
    <div class="container">
        <div class="profile-area" v-if="this.user">
            <div class="image">
                <div class="profile-image" v-if="this.user.profileImage" :style="{'background-image': 'url(' + this.user.profileImage + ')' }">
                </div>
                <div class="no-image" v-if="!this.user.profileImage">👤</div>
            </div>
            <div class="info">
                <div class="basic-info">
                    <div class="nickname">{{ this.user.nickname }}</div>
                    <div class="id">{{ "@" + this.user.id }}</div>
                </div>
                <div class="more-info">
                    <div class="birthday">🎂 {{ this.user.birthday ? this.user.birthday.replace(/-/g, '.').substring(0, 10) : "비공개" }}</div>
                    <div class="gender">⚧️ {{ this.user.gender ? (this.user.gender=='F' ? "여성" : "남성") : "비공개" }}</div>
                    <div class="email">📧 {{ this.user.email }}</div>
                </div>
            </div>
        </div>

        <div class="activity-area" v-if="this.user">
            <router-link :to="`/user/${user.id}/wish`" class="item">
                <div class="emoji shadow">🧞‍♂️</div>
                <div class="title">보고싶은<br>영화</div>
                <div class="count">
                    <div class="number">{{ this.user.wishCount }}</div>
                    <div class="extension">편</div>
                </div>
            </router-link>
            <router-link :to="`/user/${user.id}/watching`" class="item">
                <div class="emoji shadow">🍿</div>
                <div class="title">보고있는<br>영화</div>
                <div class="count">
                    <div class="number">{{ this.user.watchingCount }}</div>
                    <div class="extension">편</div>
                </div>
            </router-link>
            <router-link :to="`/user/${user.id}/comment`" class="item">
                <div class="emoji shadow">✍️</div>
                <div class="title">내가 쓴<br>코멘트</div>
                <div class="count">
                    <div class="number">{{ this.user.commentCount }}</div>
                    <div class="extension">개</div>
                </div>
            </router-link>
            <router-link :to="`/user/${user.id}/like`" class="item">
                <div class="emoji shadow">💛</div>
                <div class="title">좋아하는<br>코멘트</div>
                <div class="count">
                    <div class="number">{{ this.user.likeCount }}</div>
                    <div class="extension">개</div>
                </div>
            </router-link>
        </div>
    </div>
</template>

<script>
export default {

    beforeCreate() {
        this.axios.get("/user/"+this.$route.params.id)
        .then((response)=>{
            this.user = response.data;
        });
    },
    data() {
        return {
            user: null,
        }
    },
}
</script>

<style scoped>
.container {
    max-width: 900px;
    display: flex;
    flex-direction: column;
    gap: 72px;
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
    font-size: 20px;
    opacity: 0.7;
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
.birthday, .gender, .email {
    color: var(--G400);
}

.activity-area {
    display: flex;
    flex-wrap: wrap;
    gap: 48px 24px;
}
.item {
    border-radius: 16px;
    padding: 36px 20px 20px;
    flex-grow: 1;
    width: calc((100% - 72px)/4);
    position: relative;
    display: flex;
    flex-direction: column;
    gap: 36px;
    background: var(--G50);
    border: 1px solid var(--G200);
}
@media screen and (max-width:676px) {
    .item {
        width: calc((100% - 24px)/2);
    }
}
.title {
    font-size: 18px;
    font-weight: 600;
    line-height: 1.2;
    margin: 0 4px;
}
.emoji {
    font-size: 40px;
    position: absolute;
    top: -32px;
}
.count {
    display: flex;
    gap: 4px;
    justify-content: flex-end;
    align-items: flex-end;
    margin: 0 4px;
}
.number {
    font-size: 30px;
    font-weight: 700;
    line-height: 1.1;
}
</style>