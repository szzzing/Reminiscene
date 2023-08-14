<template>
    <div class="container">

        <title-component>
            <template v-slot:emoji>😃</template>
            <template v-slot:title>내 정보 수정</template>
        </title-component>

        <div class="flex-container">
            <div class="inner">
                <div class="title nickname" ref="nickname">🙂 닉네임</div>
                <div class="sub-title">영문, 한글을 사용해 2-8자 사이의 닉네임을 만들어주세요.</div>
                <div class="input-box"
                    v-bind:class="{ 'checked': this.nickname != '' && checkedNickname, 'unchecked': this.nickname != '' && !checkedNickname }">
                    <input type="text" v-model="nickname">
                    <i class="fa-solid fa-circle-check" v-if="this.nickname != '' && checkedNickname"></i>
                    <i class="fa-solid fa-circle-xmark" v-if="this.nickname != '' && !checkedNickname"></i>
                </div>
            </div>

            <div class="inner" ref="email">
                <div class="title">📫 이메일</div>
                <div class="sub-title">비밀번호를 찾을 때 사용할 이메일을 입력해주세요.</div>
                <div class="input-box"
                    v-bind:class="{ 'checked': this.email != '' && checkedEmail, 'unchecked': this.email != '' && !checkedEmail }">
                    <input type="text" v-model="email">
                    <i class="fa-solid fa-circle-check" v-if="this.email != '' && checkedEmail"></i>
                    <i class="fa-solid fa-circle-xmark" v-if="this.email != '' && !checkedEmail"></i>
                </div>
            </div>

            <div class="inner" ref="gender">
                <div class="title">🪞 성별</div>
                <div class="sub-title">성별을 선택해주세요.</div>
                <div class="select-box">
                    <div class="select-item" v-bind:class="{ 'selected' : this.gender=='M' }"
                    @click="this.selectGender('M')">
                        <div class="emoji">👨</div>
                        <div class="label">남자</div>
                    </div>
                    <div class="select-item" v-bind:class="{ 'selected' : this.gender=='F' }"
                    @click="this.selectGender('F')">
                        <div class="emoji">👩</div>
                        <div class="label">여자</div>
                    </div>
                    <div class="select-item" v-bind:class="{ 'selected' : this.gender==null }"
                    @click="this.selectGender(null)">
                        <div class="emoji">🧑</div>
                        <div class="label">비공개</div>
                    </div>
                </div>
            </div>

            <div class="inner" ref="birthday">
                <div class="title">🎂 생일</div>
                <div class="sub-title">태어난 날을 입력해주세요.</div>
                <div class="input-box">
                    <input type="date" min="1900-01-01" max="2023-08-30" v-model="this.birthday">
                </div>
            </div>

            <div class="inner">
                <div class="big-button" @click="modify()">
                    수정하기
                </div>
            </div>
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
            nickname: this.$store.state.auth.user.nickname,
            email: this.$store.state.auth.user.email,
            gender: this.$store.state.auth.user.gender,
            birthday: this.$store.state.auth.user.birthday,

            //  사용 가능 여부 옵션
            checkedNickname: true,
            checkedEmail: true,
            //  경고 메세지
            message: '',
            undo: false,
        }
    },
    watch: {
        //  닉네임 사용 가능 여부, 사용 가능할 시 중복 여부 확인
        nickname() {
            let reg = /^[a-zA-Zㄱ-ㅎ가-힣]{2,8}$/g;
            const checked = reg.test(this.nickname);
            this.checkedNickname = checked;
            // 사용 가능할 시, 중복 여부 확인
            if (checked) {
                this.checkNickname();
            }
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
        }
    },
    methods: {
        //  아이디 중복 여부 체크
        checkNickname() {
            if(this.nickname==this.$store.state.auth.user.nickname) {
                this.checkedNickname = true;
            } else {
                this.axios.get("/mypage/check/nickname/" + this.nickname)
                    .then((response) => {
                        this.checkedNickname = response.data;
                    })
            }
        },
        //  이메일 중복 여부 체크
        checkEmail() {
            if(this.email==this.$store.state.auth.user.email) {
                this.checkedEmail = true;
            } else {
                this.axios.get("/mypage/check/email/" + this.email)
                    .then((response) => {
                        this.checkedEmail = response.data;
                    })
            }

        },
        //  성별 선택
        selectGender(gender) {
            this.gender = gender;
        },

        //  내 정보 수정
        modify() {
            // 입력 여부 확인
            if (!this.checkedNickname) {
                this.$refs.nickname.scrollIntoView({ behavior: "smooth" });
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"닉네임을 확인해주세요." });
            } else if (!this.checkedEmail) {
                this.$refs.email.scrollIntoView({ behavior: "smooth" });
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"이메일을 확인해주세요." });
            } else {
                const nickname = this.nickname;
                const email = this.email;
                const gender = this.gender;
                const birthday = this.birthday;
                this.axios.post("/mypage/modify", { nickname, email, gender, birthday })
                    .then(() => {
                        this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"내 정보를 수정했어요." });
                        this.$router.push({ path: '/mypage' });
                    });
            }
        },

        // 입력 관련 메세지
        alert(message) {
            this.undo = true;
            this.message = message;
            setTimeout(() => this.undo = false, 3000);
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
    margin: auto;
}

.title {
    max-width: 600px;
    font-size: 24px;
    margin-bottom: 4px;
}

.sub-title {
    color: var(--G400);
    font-size: 18px;
}

.input-box, .select-box {
    margin: 12px 0 0;
    display: flex;
    gap: 20px;
    align-items: center;
}

input {
    font-size: 18px;
}

.select-item > * {
    text-align: center;
    filter: grayscale(100%);
    color: var(--G500);
    cursor: pointer;
}
.select-item.selected > * {
    filter: grayscale(0%);
    color: var(--G1000);
}
.select-item > .emoji {
    opacity: 0.5;
}
.select-item.selected > .emoji {
    opacity: 1;
}
.select-item .emoji {
    font-size: 48px;
    line-height: 1.2;
}
.select-item .label {
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

.big-button {
    width: 120px;
    margin: 0 0 0 auto;
}
</style>