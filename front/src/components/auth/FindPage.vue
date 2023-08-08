<template>
    <div class="container">
        <title-item>
            <template v-slot:title>비밀번호 찾기</template>
        </title-item>
        <div class="sub-text">
            가입시 계정 인증에 사용한 <b>이메일</b>을 입력해주세요.<br>
            비밀번호를 바꿀 수 있는 링크를 보내드릴게요 🔑
        </div>

        <div class="input-area">
            <div class="input-box">
                <input type="text" placeholder="이메일" v-model="email"
                @keyup.enter="this.login()">
            </div>
            <div class="medium-button"
            v-on:click="this.login()">
                링크 보내기
            </div>
        </div>
    </div>
</template>

<script>
import TitleItem from '../item/TitleItem.vue'

export default {
    components: {
        TitleItem,
    },
    data() {
        return {
            email: '',
            pw: '',
        }
    },
    methods: {
        // 로그인
        login() {
            const id = this.id.trim();
            const pw = this.pw.trim();
            
            if(id!='' && pw!='') {
                this.$store.dispatch("auth/login", {id, pw});
            } else {
                alert("아이디와 비밀번호를 입력해주세요.");
            }
        },
    }
}
</script>

<style scoped>
    .container {
        max-width: 400px;
    }
    .sub-text, .sub-text * {
        color: var(--G500);
    }
    .input-area {
        margin: 24px auto;
    }
    .input-box {
        line-height: 48px;
        border-radius: 16px;
        padding: 12px 24px;
        height: unset;
    }
    input {
        font-size: 18px;
    }
    
    .medium-button {
        width: 100px;
        margin: 20px 0 0 auto;
    }
</style>