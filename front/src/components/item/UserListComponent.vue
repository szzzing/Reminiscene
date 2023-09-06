<template>
    <transition-group v-if="this.list.length!=0" v-bind:class="{'hide' : this.hide}" id="profile-list" tag="div" class="list">
        <router-link class="item" v-for="(user) in this.list" :key="user" :to="`/user/${user.id}`">
            <div class="image">
                <div class="profile-image" v-if="user.profileImage" :style="{'background-image': `url(${user.profileImage}` }">
                </div>
                <div class="no-image" v-if="!user.profileImage"><i class="fa-solid fa-user"></i></div>
            </div>
            <div class="info">
                <div class="nickname">{{ user.nickname!=null ? user.nickname : user.id }}</div>
                <div class="id">{{ "@"+user.id }}</div>
                <div class="commentCount">{{ user.commentCount+"개의 평가" }}</div>
            </div>
        </router-link>
    </transition-group>
</template>

<script>
export default {
    props: [
        'list',
    ],
}
</script>

<style scoped>
.query {
    font-size: 18px;
}
#profile-list {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
}
.item {
    border-bottom: 1px solid var(--G100);
    width: calc((100% - 40px)/3);
    padding-bottom: 20px;
    display: flex;
    gap: 16px;
}
@media screen and (max-width:768px) {
    .item {
        width: calc((100% - 20px)/2);
    }
}
@media screen and (max-width:474px) {
    .item {
        width: 100%;
    }
}
.profile-image, .no-image {
    width: 72px;
    height: 72px;
    font-size: 36px;
}
#profile-list .info {
    display: flex;
    flex-direction: column;
}
.id {
    color: var(--G400);
    flex-grow: 1;
    line-height: 1;
}
.nickname {
    font-weight: 600;
    line-height: 1.4;
}
.commentCount {
    color: var(--G500);
    font-size: 14px;
}
</style>