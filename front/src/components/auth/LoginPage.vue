<template>
    <div class="container">

        <title-component>
            <template v-slot:emoji>💛</template>
            <template v-slot:title>로그인</template>
        </title-component>
        
        <div class="input-area">
            <div class="input-box">
                <input type="text" placeholder="아이디" v-model="id" maxlength="20"
                @keyup.enter="this.login()">
                <input type="password" placeholder="비밀번호" v-model="pw" maxlength="20"
                @keyup.enter="this.login()">
            </div>
            <div class="medium-button"
            v-on:click="this.login()">
                로그인
            </div>
        </div>
        <div class="option">처음 방문하셨다면
            <router-link class="option-item" to="/auth/register">회원가입</router-link>
        </div>
        <div class="option">
            <router-link to="/auth/find">아이디, 비밀번호를 잊으셨나요?</router-link>
        </div>
    </div>
</template>

<script>
import TitleComponent from '../item/TitleComponent.vue'

export default {
    components: {
        TitleComponent,
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
                    this.$store.commit("auth/setAccessToken", response.headers.access_token);
                    // 사용자 정보 저장
                    this.$store.commit("auth/setUser", JSON.parse(response.headers.user));
                    // 페이지 이동
                    if(this.$store.state.auth.user.role=='A') {
                        this.$router.replace({ path: "/admin" });
                    } else {
                        this.$router.replace({ path: this.$store.state.local.location==null ? '/' : this.$store.state.local.location });
                    }
                })
                .catch(()=>{
                    this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"로그인에 실패했어요." });
                });
            } else {
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"아이디와 비밀번호를 입력해주세요." });
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
    margin-bottom: 24px;
}
.input-box {
    line-height: 48px;
    border-radius: 8px;;
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