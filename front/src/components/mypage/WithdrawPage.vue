<template>
    <confirm-modal-component v-if="this.withdrawModal" @closeConfirmModal="this.withdrawModal=false"
    @no="this.withdrawModal=false"
    @yes="withdraw">
        <template v-slot:emoji>🥺</template>
        <template v-slot:text>정말 탈퇴하실건가요?</template>
    </confirm-modal-component>

    <div id="withdraw" class="container">
        <title-component>
            <template v-slot:emoji>🥺</template>
            <template v-slot:title>회원탈퇴</template>
        </title-component>
        
        <div class="inner">
            <div class="sub-text">
                <span class="accent">{{ this.$store.state.auth.user.id ? this.$store.state.auth.user.id : '' }}</span> 아이디를 탈퇴하실건가요?
                <br>탈퇴한 후에는 다시 로그인할 수 없어요.
            </div>
            <div class="input-box">
                <input type="password" v-model="pw" maxlength="20" placeholder="비밀번호">
            </div>
            <div class="medium-button" @click="clickWithdraw">탈퇴하기</div>
        </div>
    </div>
</template>

<script>
import TitleComponent from '../item/TitleComponent.vue';
import ConfirmModalComponent from '../modal/ConfirmModalComponent.vue'

export default {
    components: {
        ConfirmModalComponent,
        TitleComponent,
    },
    data() {
        return {
            pw: null,
            withdrawModal: false,
        }
    },
    methods: {
        clickWithdraw() {
            if(!this.pw || this.pw.length==0) {
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"비밀번호를 입력해주세요." });
                return;
            }
            const params = { pw: this.pw, };
            this.axios.get("/user?type=matchPw", {params})
            .then((response)=>{
                if(response.data==true) {
                    this.withdrawModal = true;
                } else {
                    this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"비밀번호가 틀렸어요." })
                }
            });
        },
        withdraw() {
            this.axios.delete(`user/${this.$store.state.auth.user.id}`)
            .then((response)=>{
                if(response.data==true) {
                    this.$store.commit('auth/logout');
                    this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"탈퇴되었어요." });
                }
            })
        }
    }
}
</script>

<style scoped>
.container {
    max-width: 440px;
}
.inner {
    display: flex;
    flex-direction: column;
    gap: 24px;
}
input {
    font-size: 18px;
}
.input-box {
    margin: 0;
}
.medium-button {
    width: 90px;
    margin-left: auto;
}
.sub-text {
    color: var(--G500);
}
.accent {
    font-weight: 600;
    color: var(--FOCUS);
}
</style>