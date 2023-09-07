<template>
    <send-email-modal v-bind:email="email" v-if="this.emailModal" @closeEmailModal="this.emailModal=false"></send-email-modal>

    <title-component>
        <template v-slot:emoji>🔎</template>
        <template v-slot:title>아이디 찾기</template>
    </title-component>
    <div class="sub-text">
        가입시 인증에 사용한 <span class="accent">이메일</span>을 입력해주세요.<br>
        아이디 <span class="accent">힌트</span>를 알려드릴게요.
    </div>

    <div class="input-area">
        <div class="input-box">
            <input type="text" placeholder="이메일" v-model="email" @keyup.enter="this.checkEmail()">
        </div>
        <div class="medium-button" v-on:click="this.checkEmail()">
        힌트 받기
        </div>
    </div>
</template>

<script>
import TitleComponent from '../item/TitleComponent.vue'
import SendEmailModal from './SendEmailModal.vue';

export default {
    components: {
        TitleComponent,
        SendEmailModal,
    },
    data() {
        return {
            email: null,
            emailModal: false,
        }
    },
    methods: {
        // 이메일 전송
        sendEmail() {
            const params = { to: this.email.trim() };

            this.axios.post("/email/find/id", params)
            .then((response)=>{
                if(response.data==true) {
                    this.emailModal = true;
                } else {
                    this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"일치하는 회원 정보가 없어요." })
                }
            })
        },
        checkEmail() {
            // 입력한 이메일 유효성 검사
            let reg = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
            const checkEmail = reg.test(this.email);
            if(!checkEmail) {
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"올바른 이메일을 입력해주세요." });
            }
            else {
                // 이메일 전송
                this.sendEmail();
            }
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
    width: 96px;
    margin: 20px 0 0 auto;
}
.accent {
    font-weight: 500;
    color: var(--FOCUS);
}
</style>