<template>
    <div id="comment" class="container">
        <div class="title">
            코멘트
            <span class="sub-title">3000+</span>
        </div>
        <div class="best-comment">
            <div class="item">
                <div class="profile">
                    <div v-if="user && user.profileImage" class="profile-image" :style="{'background-image': 'url(' + user.profileImage + ')' }"></div>
                    <!-- <div class="no-image" v-if="user && !user.profileImage">👤</div> -->
                    <div class="no-image" v-if="!user">👤</div>
                    <div class="nickname">찌니</div>
                    <div class="status">🙏 보고싶어요</div>
                </div>
                <div class="text">
                    줄리어스 로버트 오펜하이머..<br>
                    줄리어스 로버트 오펜하이머..<br>
                    자기 이야기가 영화로 만들어진다니.<br>
                    로버트는 얼마나 좋았을까.
                </div>
                <div class="interest">
                    <div class="like">👍 20</div>
                    <div class="reply">💭 8</div>
                </div>
            </div>
            <div class="item item-shadow">
                <div class="profile">
                    <div v-if="user && user.profileImage" class="profile-image" :style="{'background-image': 'url(' + user.profileImage + ')' }"></div>
                    <!-- <div class="no-image" v-if="user && !user.profileImage">👤</div> -->
                    <div class="no-image" v-if="!user">👤</div>
                    <div class="nickname">찌니</div>
                    <div class="status">🙏 보고싶어요</div>
                </div>
                <div class="text">
                    8월 14일 입대 진짜 피눈물나네 ㅋㅋㅋㅋ
                </div>
                <div class="interest">
                    <div class="like">👍 20</div>
                    <div class="reply">💭 8</div>
                </div>
            </div>
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
    padding: 60px 0 0;
    max-width: 1280px;
    display: flex;
    flex-direction: column;
    gap: 24px;
}
.title {
    font-size: 24px;
    font-weight: 700;
}
.sub-title {
    font-weight: 400;
    color: var(--FOCUS);
    margin-left: 4px;
}

.best-comment {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
}
.item {
    border-radius: 16px;
    flex-basis: calc((100% - 60px)/4);
    padding: 20px 16px;
    display: flex;
    flex-direction: column;
    gap: 20px;
    cursor: pointer;
    background: var(--G50);
}
@media screen and (max-width:960px) {
    .item {
        flex-basis: calc((100% - 40px)/3);
    }
}
@media screen and (max-width:800px) {
    .item {
        flex-basis: calc((100% - 20px)/2);
    }
}
@media screen and (max-width:500px) {
    .item {
        flex-basis: 100%;
    }
}
.profile {
    display: flex;
    align-items: center;
    gap: 8px;
}
.text {
    flex-grow: 1;
    padding: 20px 0;
    border-top: 1px solid var(--G100);
    border-bottom: 1px solid var(--G100);
    text-overflow: ellipsis;
    overflow: hidden;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 7;
}
.interest {
    display: flex;
    gap: 16px;
}

.nickname {
    flex-grow: 1;
    font-weight: 500;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.status {
    background: var(--G200);
    border-radius: 16px;
    padding: 4px 10px;
    font-size: 14px;
    flex-shrink: 0;
}

.profile-image, .no-image {
    width: 30px;
    height: 30px;
    background-size: cover;
    background-position: center;
    border-radius: 50%;
    background-color: var(--G200);
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
}
.no-image {
    font-size: 16px;
}
.interest * {
    color: var(--G500);
    font-size: 14px;
}
</style>