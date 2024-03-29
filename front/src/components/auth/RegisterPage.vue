<template>
    <div class="container">

        <email-modal-component v-if="this.emailModal"
        v-bind:url="url"
        v-bind:params="params"
        @closeEmailModal="this.emailModal=false"
        @succeedEmail="this.emailSuccess=true">
        </email-modal-component>

        <title-component>
            <template v-slot:emoji>🪪</template>
            <template v-slot:title>회원가입</template>
        </title-component>

        <div class="flex-container">
            <div class="inner">
                <div class="title id" ref="id">💗 아이디</div>
                <div class="sub-title">영문, 숫자, 특수문자 -, _를 포함해 6-12자의 아이디를 만들어주세요.</div>
                <div class="input-box"
                    v-bind:class="{ 'checked': this.id && checkedId, 'unchecked': this.id && !checkedId }">
                    <input type="text" v-model="id" maxlength="12">
                    <i class="fa-solid fa-circle-check" v-if="this.id && checkedId"></i>
                    <i class="fa-solid fa-circle-xmark" v-if="this.id && !checkedId"></i>
                </div>
            </div>

            <div class="inner pw" ref="pw">
                <div class="title">🔑 비밀번호</div>
                <div class="sub-title">영문, 숫자, 특수문자를 포함해 8-20자의 비밀번호를 만들어주세요.</div>
                <div class="input-box"
                    v-bind:class="{ 'checked': this.pw && checkedPw, 'unchecked': this.pw && !checkedPw }">
                    <input type="password" v-model="pw" maxlength="20">
                    <i class="fa-solid fa-circle-check" v-if="this.pw && checkedPw"></i>
                    <i class="fa-solid fa-circle-xmark" v-if="this.pw && !checkedPw"></i>
                </div>
            </div>

            <div class="inner pw2" ref="pw2">
                <div class="title">🔐 비밀번호 확인</div>
                <div class="sub-title">비밀번호를 다시 입력해주세요.</div>
                <div class="input-box"
                    v-bind:class="{ 'checked': this.pw2 && checkedPw2, 'unchecked': this.pw2 && !checkedPw2 }">
                    <input type="password" v-model="pw2" maxlength="20">
                    <i class="fa-solid fa-circle-check" v-if="this.pw2 && checkedPw2"></i>
                    <i class="fa-solid fa-circle-xmark" v-if="this.pw2 && !checkedPw2"></i>
                </div>
            </div>

            <div class="inner email" ref="email">
                <div class="title">📧 이메일</div>
                <div class="sub-title">
                    {{ checkedEmail && emailSuccess ? timer : "자주 사용하는 이메일을 인증해주세요." }}
                    <span class="email-button" v-if="this.checkedEmail && !this.emailSuccess" @click="this.clickEmailButton()">인증받기</span>
                </div>
                <div class="input-box"
                    v-bind:class="{ 'checked': this.email && checkedEmail, 'unchecked': this.email && !checkedEmail }">
                    <input type="text" v-model="email" maxlength="40" :readonly="this.emailSuccess">
                    <i class="fa-solid fa-circle-check" v-if="this.email && checkedEmail"></i>
                    <i class="fa-solid fa-circle-xmark" v-if="this.email && !checkedEmail"></i>
                </div>
            </div>

            <div class="inner">
                <div class="medium-button" @click="register()">
                    계정 만들기
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import TitleComponent from '../item/TitleComponent.vue'
import EmailModalComponent from '../modal/EmailModalComponent.vue';

export default {
    components: {
        TitleComponent,
        EmailModalComponent,
    },
    data() {
        return {
            id: '',
            pw: '',
            pw2: '',
            email: '',
            //  사용 가능 여부 옵션
            checkedId: false,
            checkedPw: false,
            checkedPw2: false,
            checkedEmail: false,
            //  경고 메세지
            message: '',
            undo: false,
            //  이메일 인증 모달
            emailModal: false,
            emailSuccess: false,
            url: "/email/code",
            params: null,
            // 이메일 인증 유효시간 체크
            interval: null,
            timer: null,
        }
    },
    watch: {
        //  아이디 사용 가능 여부, 사용 가능할 시 중복 여부 확인
        id() {
            let reg = /^[a-zA-Z][a-zA-Z0-9-_]{5,11}$/g;
            const checked = reg.test(this.id);
            this.checkedId = checked;
            // 사용 가능할 시, 중복 여부 확인
            if (checked) {
                this.checkId();
            }
        },
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
        //  이메일 사용 가능 여부, 사용 가능할 시 중복 여부 확인
        email() {
            let reg = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i;
            const checked = reg.test(this.email);
            this.checkedEmail = checked;
            //  사용 가능할 시, 중복 여부 확인
            if (checked) {
                this.checkEmail();
            }
        },
        // 이메일 인증 후 3분동안 유지
        emailSuccess() {
            if(this.emailSuccess) {
                clearInterval(this.interval);
                this.timer = `인증은 3분 0초 동안 유지할 수 있어요.`
                let time = 179;
    
                this.interval = setInterval(()=>{
                    const min = parseInt(time/60);
                    const sec = String(time%60);
                    this.timer = `인증은 ${min}분 ${sec}초 동안 유지할 수 있어요.`;
                    time--;
    
                    if(time<0) {
                        this.emailSuccess = false;
                        this.email = '';
                        clearInterval(this.interval);
                    }
                }, 1000);
            }
        },
    },
    methods: {
        //  아이디 중복 여부 체크
        checkId() {
            const params = {id : this.id};
            this.axios.get("/user?type=check", {params})
                .then((response) => {
                    this.checkedId = response.data;
                })
        },
        //  이메일 중복 여부 체크
        async checkEmail() {
            const params = {email : this.email};
            await this.axios.get("/user?type=check", {params})
                .then((response) => {
                    this.checkedEmail = response.data;
                })
        },
        //  회원가입
        register() {
            // 입력 여부 확인
            if (!this.checkedId) {
                this.$refs.id.scrollIntoView({ behavior: "smooth" });
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"아이디를 확인해주세요." });
            } else if (!this.checkedPw) {
                this.$refs.pw.scrollIntoView({ behavior: "smooth" });
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"비밀번호를 확인해주세요." });
            } else if (!this.checkedPw2) {
                this.$refs.pw2.scrollIntoView({ behavior: "smooth" });
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"비밀번호를 확인해주세요." });
            } else if (!this.checkedEmail) {
                this.$refs.email.scrollIntoView({ behavior: "smooth" });
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"이메일을 확인해주세요." });
            } else if (!this.emailSuccess) {
                this.$refs.email.scrollIntoView({ behavior: "smooth" });
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"이메일 인증을 완료해주세요." });
            } else {
                const id = this.id;
                const pw = this.pw;
                const email = this.email;
                this.axios.post("/user", { id, pw, email })
                    .then((response) => {
                        if(response.status==201) {
                            this.$store.commit("modal/setAlert", { alertEmoji:"🎉", alertText:"회원가입에 성공했어요." });
                            this.$router.push({ path: '/auth/login' });
                        }
                    })
            }
        },
        // 인증하기 버튼 클릭
        clickEmailButton() {
            const params = {email : this.email};
            this.axios.get("/user?type=check", {params})
            .then((response) => {
                if(response.data==true) {
                    this.params = {
                        to: this.email.trim(),
                    }
                    this.emailModal = true;
                } else {
                    this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"다른 이메일로 시도해주세요." });
                }
            })
        },
    }
}
</script>

<style scoped>
.container {
    max-width: 600px;
}
.flex-container {
    display: flex;
    flex-direction: column;
    gap: 48px;
}

.title {
    max-width: 600px;
    font-size: 20px;
    font-weight: 500;
}

.sub-title {
    color: var(--G500);
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
    width: 100px;
    margin: 0 0 0 auto;
}
.email-button {
    cursor: pointer;
    color: var(--FOCUS);
    font-weight: 600;
}
</style>