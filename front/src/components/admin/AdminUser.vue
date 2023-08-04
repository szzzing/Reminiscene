<template>
  <div id="user">
    <div class="title">
        사용자 관리
    </div>
    <div class="content">
        <ul class="list">
            <li class="list-item">
                <div>아이디</div>
                <div>비밀번호</div>
                <div>닉네임</div>
                <div>이메일</div>
            </li>
        </ul>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
axios.defaults.withCredentials = true;

export default {
    methods: {
        getUserData() {
            axios
            .get('http://localhost:8080/admin/selectAll')
            .then(function(response) {
                console.log(response);
            });
        }
    },
    created() {
        this.getUserData();
    }
}
</script>

<style scoped>
    .title {
        text-align: center;
        font-size: 24px;
        font-weight: 700;
        margin: 60px 0;
    }
    .list-item {
        display: flex;
    }
    .list-item > div {
        width: calc(100% / 4);
        text-align: center;
        font-size: 18px;
        font-weight: 700;
    }
</style>