<template>
    <div class="container">
        <app-title></app-title>
        <div class="input-area">
            <div class="input-box">
                <input type="text" placeholder="아이디" v-model="id"
                @keyup.enter="this.login()">
                <input type="password" placeholder="비밀번호" v-model="pw"
                @keyup.enter="this.login()">
            </div>
            <div class="big-button"
            v-on:click="this.login()">
                로그인
            </div>
        </div>
        <div class="option">
            처음 방문하셨다면
            <router-link class="option-item" to="/register">가입하기</router-link>
        </div>
        <div class="option">
            <router-link class="option-item" to="/find">비밀번호를 잊으셨나요?</router-link>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import AppTitle from '../common/AppTitle.vue'

export default {
    components: {
        AppTitle
    },
    data() {
        return {
            id: '',
            pw: '',
        }
    },
    methods: {
        // 로그인
        login() {
            const id = this.id.trim();
            const pw = this.pw.trim();
            
            if(id!='' && pw!='') {
                axios.post("http://localhost:8080/login",{id, pw})
                .then((response)=>{
                    if(response.status == 200) {
                        console.log(response.headers);
                        this.$store.dispatch("user/getUser", id);
                        this.$router.push({ path: '/' });
                    }
                })
                .catch(function() {
                    alert("로그인에 실패했습니다.");
                });
            } else {
                alert("아이디와 비밀번호를 입력해주세요.");
            }
        },
    }
}
</script>

<style scoped>
    .container {
        max-width: 1280px;
    }
    div {
        text-align: center;
    }
    .input-area {
        max-width: 240px;
        margin: 48px auto 24px;
    }
    .input-box {
        line-height: 36px;
        border-radius: 16px;
        padding: 12px 24px;
        height: unset;
    }
    input {
        font-size: 18px;
    }
    .option {
        line-height: 30px;
        color: var(--G400);
    }
    .option a {
        font-weight: 500;
        color: var(--FOCUS);
    }
</style>