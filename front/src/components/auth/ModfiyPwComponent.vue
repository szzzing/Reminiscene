<template>
    <route-modal-component v-bind:route="`/login`" v-if="this.emailChanged">
        <template v-slot:emoji>😀</template>
        <template v-slot:text>비밀번호를 다시 설정했어요.</template>
        <template v-slot:route>로그인하러 가기</template>
    </route-modal-component>

    <title-component>
        <template v-slot:emoji>🔒</template>
        <template v-slot:title>비밀번호 재설정</template>
    </title-component>
    
    <div class="inner">
        <div class="item">
            <div class="title">🔑 비밀번호</div>
            <div class="sub-title">영문, 숫자, 특수문자를 포함해 8-20자의 비밀번호를 만들어 주세요.</div>
            <div class="input-box"
                v-bind:class="{ 'checked': this.pw && checkedPw, 'unchecked': this.pw && !checkedPw }">
                <input type="password" v-model="pw" maxlength="20">
                <i class="fa-solid fa-circle-check" v-if="this.pw && checkedPw"></i>
                <i class="fa-solid fa-circle-xmark" v-if="this.pw && !checkedPw"></i>
            </div>
        </div>

        <div class="item">
            <div class="title">🔐 비밀번호 확인</div>
            <div class="sub-title">비밀번호를 다시 입력해주세요.</div>
            <div class="input-box"
                v-bind:class="{ 'checked': this.pw2 && checkedPw2, 'unchecked': this.pw2 && !checkedPw2 }">
                <input type="password" v-model="pw2" maxlength="20">
                <i class="fa-solid fa-circle-check" v-if="this.pw2 && checkedPw2"></i>
                <i class="fa-solid fa-circle-xmark" v-if="this.pw2 && !checkedPw2"></i>
            </div>
        </div>

        <div class="medium-button" @click="modifyPw()">변경하기</div>
    </div>
</template>

<script>
import TitleComponent from '../item/TitleComponent.vue'
import RouteModalComponent from '../modal/RouteModalComponent.vue';

export default {
    components: {
        TitleComponent,
        RouteModalComponent,
    },
    data() {
        return {
            pw: null,
            pw2: null,
            checkedPw: null,
            checkedPw2: null,
            emailChanged: false,
        }
    },
    props: [
        'id',
    ],
    watch: {
        //  비밀번호 사용 가능 여부 확인
        pw() {
            let reg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/g;
            const checked = reg.test(this.pw);
            this.checkedPw = checked;
            this.checkedPw2 = this.pw == this.pw2;
        },
        //  비밀번호 일치 여부 확인
        pw2() {
            this.checkedPw2 = this.pw == this.pw2;
        },
    },
    methods: {
        modifyPw() {
            const params = {
                id: this.id,
                pw: this.pw,
            }
            this.axios.put("/user/pw", params)
            .then((response)=>{
                if(response.data==true) {
                    this.emailChanged = true;
                }
            });
        }
    }
}
</script>

<style scoped>
.inner {
    display: flex;
    flex-direction: column;
    gap: 24px;
}
.title {
    max-width: 600px;
    font-size: 20px;
    font-weight: 500;
}
.sub-title {
    color: var(--G400);
    word-break: keep-all;
}
.input-box {
    margin: 12px 0 0;
    display: flex;
    gap: 16px;
    align-items: center;
}
input {
    font-size: 18px;
}
.fa-solid {
    font-size: 24px;
}
.fa-circle-check {
    color: var(--GREEN);
}
.fa-circle-xmark {
    color: var(--RED);
}
.checked {
    border: 2px solid rgba(99, 206, 86, 0.5);
}
.unchecked {
    border: 2px solid rgba(237, 106, 94, 0.5);
}
.medium-button {
    width: 90px;
    margin: 0 0 0 auto;
}
</style>