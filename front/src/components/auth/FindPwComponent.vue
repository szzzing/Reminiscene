<template>
    <email-modal-component v-if="this.emailModal"
    v-bind:email="email"
    v-bind:url="url"
    v-bind:params="params"
    @closeEmailModal="this.emailModal=false"
    @succeedEmail="this.$emit('succeedEmail', this.id)">
    </email-modal-component>

    <title-component>
        <template v-slot:emoji>🔎</template>
        <template v-slot:title>비밀번호 찾기</template>
    </title-component>
    <div class="sub-text">
        <span class="accent">아이디</span>와 <span class="accent">이메일</span>을 입력해주세요.<br>이메일 인증 후 비밀번호를 다시 설정할 수 있어요.
    </div>

    <div class="input-area">
        <div class="input-box">
            <input type="text" placeholder="아이디" v-model="id" @keyup.enter="this.checkInput()">
            <input type="text" placeholder="이메일" v-model="email" @keyup.enter="this.checkInput()">
        </div>
        <div class="medium-button" v-on:click="this.checkInput()">인증하기</div>
    </div>
</template>

<script>
import TitleComponent from '../item/TitleComponent.vue'
import EmailModalComponent from '../modal/EmailModalComponent.vue'

export default {
    components: {
        TitleComponent,
        EmailModalComponent,
    },
    data() {
        return {
            id: null,
            email: null,
            emailModal: false,
            url: "/email/find/pw",
            params: null,
            emailSuccess: false,
        }
    },
    emits: [
        'succeedEmail',
    ],
    methods: {
        checkId() {
            let regId = /^[a-zA-Z][a-zA-Z0-9-_]{5,11}$/g;
            const checkId = regId.test(this.id);
            if(!checkId) {
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"올바른 아이디를 입력해주세요." });
                return false;
            }
        },
        checkEmail() {
            let regEmail = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
            const checkEmail = regEmail.test(this.email);
            if(!checkEmail) {
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"올바른 이메일을 입력해주세요." });
                return false;
            }
        },
        // 유효성 체크
        checkInput() {
            let regId = /^[a-zA-Z][a-zA-Z0-9-_]{5,11}$/g;
            const checkId = regId.test(this.id);
            if(!checkId) {
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"올바른 아이디를 입력해주세요." });
                return false;
            }
            let regEmail = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
            const checkEmail = regEmail.test(this.email);
            if(!checkEmail) {
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"올바른 이메일을 입력해주세요." });
                return false;
            }
            this.sendEmail();
        },
        sendEmail() {
            const params = {
                id: this.id,
                email: this.email,
            };
            this.axios.get("/user/check", {params})
            .then((response) => {
                console.log(response.data);
                if(response.data==true) {
                    this.params = {
                        to: this.email.trim(),
                        id: this.id.trim(),
                    }
                    this.emailModal = true;
                } else {
                    this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"일치하는 회원 정보가 없어요." });
                }
            })
        },
    }
}
</script>

<style scoped>
.sub-text, .sub-text * {
    color: var(--G500);
}
.input-area {
    margin: 24px 0 0;
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
    width: 90px;
    margin: 20px 0 0 auto;
}
.accent {
    font-weight: 600;
    color: var(--FOCUS);
}
</style>