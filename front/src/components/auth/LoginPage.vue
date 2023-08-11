<template>
    <div class="container">
        
        <alert-component v-bind:alert="undo">
            <template v-slot:emoji>⚠️</template>
            <template v-slot:text>아이디와 비밀번호를 입력해주세요.</template>
        </alert-component>
       
        <alert-component v-bind:alert="failed">
            <template v-slot:emoji>🥺</template>
            <template v-slot:text>정확한 로그인 정보를 입력해주세요.</template>
        </alert-component>
        
        <title-component>
            <template v-slot:emoji>🔑</template>
            <template v-slot:title>로그인</template>
        </title-component>
        
        <div class="input-area">
            <div class="input-box">
                <input type="text" placeholder="아이디" v-model="id"
                @keyup.enter="this.login()">
                <input type="password" placeholder="비밀번호" v-model="pw"
                @keyup.enter="this.login()">
            </div>
            <div class="medium-button"
            v-on:click="this.login()">
                로그인
            </div>
        </div>
        <div class="option">
            처음 방문하셨다면
            <router-link class="option-item" to="/auth/register">회원가입</router-link>
        </div>
        <div class="option">
            <router-link to="/auth/find">비밀번호를 잊으셨나요?</router-link>
        </div>
    </div>
</template>

<script>
import TitleComponent from '../item/TitleComponent.vue'
import AlertComponent from '../modal/AlertComponent.vue'

export default {
    components: {
        TitleComponent,
        AlertComponent,
    },
    data() {
        return {
            id: '',
            pw: '',
            undo: false,
            failed: false,
        }
    },
    methods: {
        // 로그인
        login() {
            const id = this.id.trim();
            const pw = this.pw.trim();
            
            if(id!='' && pw!='') {
                this.axios.post("/login",{id, pw})
                .then(response=>{
                    // 토큰 저장
                    this.$store.commit("auth/setToken", response.headers.token);
                    // 사용자 정보 저장
                    this.$store.commit("auth/setUser", JSON.parse(response.headers.user));
                    // 페이지 이동 - 이전 페이지 정보가 있을 시 이전페이지, 없을 시 메인페이지
                    this.$router.push({ path: this.$store.state.local.location==null ? '/' : this.$store.state.local.location });
                })
                .catch(()=>{
                    this.failed = true;
                    setTimeout(() => this.failed = false, 3000);
                });
            } else {
                this.undo = true;
                setTimeout(() => this.undo = false, 3000);
            }
        },
    }
}
</script>

<style scoped>
.container {
    max-width: 400px;
}
.input-area {
    margin: 48px auto 24px;
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
    width: 80px;
    margin: 20px 0 0 auto;
}
.option {
    line-height: 24px;
    color: var(--G400);
}
.option-item {
    font-weight: 500;
    margin-left: 2px;
    color: var(--FOCUS);
}
.vibration {
    border: 2px solid var(--RED);
}
</style>