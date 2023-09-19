<template>
    <div class="profile-area" v-if="this.user">
        <div class="image">
            <div class="profile-image" v-if="this.user.profileImage" :style="{'background-image': 'url(' + this.user.profileImage + ')' }">
            </div>
            <div class="no-image" v-if="!this.user.profileImage"><i class="fa-solid fa-user"></i></div>
        </div>
        <div class="info">
            <div class="basic-info">
                <div class="nickname">{{ this.user.nickname ? this.user.nickname : this.user.id }}</div>
            </div>
            <div class="more-info">
                <div class="birthday">🎂 {{ this.user.birthday ? this.user.birthday.replace(/-/g, '.').substring(0, 10) : "비공개" }}</div>
                <div class="gender">⚧️ {{ this.user.gender ? (this.user.gender=='F' ? "여성" : "남성") : "비공개" }}</div>
            </div>
            <div class="activity-info">
                <div class="option"><b>{{ this.user.commentCount }}</b>팔로워</div>
                <div class="option"><b>{{ this.user.commentCount }}</b>팔로잉</div>
            </div>
        </div>
    </div>

    <div class="active-area">
        <div class="category">
            <div class="option" @click="this.$emit('clickCategory', 'wish')" :class="{'active': this.category=='wish'}">보고싶어요</div>
            <div class="option" @click="this.$emit('clickCategory', 'watching')" :class="{'active': this.category=='watching'}">보는 중</div>
            <div class="option" @click="this.$emit('clickCategory', 'comment')" :class="{'active': this.category=='comment'}">코멘트</div>
            <div class="option" @click="this.$emit('clickCategory', 'like')" :class="{'active': this.category=='like'}">좋아요</div>
            <div class="option"></div>
        </div>
    </div>
</template>

<script>
export default {
    props: [
        'user',
        'category',
    ],
    emits: [
        'clickCategory',
    ],
}
</script>

<style scoped>
.profile-area {
    display: flex;
    gap: 24px;
    align-items: center;
}
.profile-image, .no-image {
    width: 140px;
    height: 140px;
}
.no-image {
    font-size: 72px;
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
    color: var(--G500);
}
.info {
    display: flex;
    flex-direction: column;
    gap: 8px;
}
.activity-info {
    display: flex;
    gap: 16px;
}
.activity-info .option {
    color: var(--G600);
}
.activity-info .option b {
    margin-right: 4px;
}
.basic-info,
.more-info {
    display: flex;
    flex-direction: column;
}
.basic-info {
    gap: 8px;
}
.basic-info * {
    line-height: 1;
}
.birthday, .gender, .email {
    color: var(--G400);
}

.active-area .category {
    display: flex;
}
.active-area .category .option {
    padding: 8px 12px;
}
.active-area .category .option {
    border-bottom: 1px solid var(--G100);
    color: var(--G500);
    font-weight: 600;
    cursor: pointer;
}
.active-area .category .option:last-child {
    flex-grow: 1;
    cursor: none;
}
.active-area .category .option.active {
    border-bottom: 3px solid var(--FOCUS);
    color: var(--G800);
}
</style>