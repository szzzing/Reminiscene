<template>
    <div id="modify" class="container">
        <email-modal-component v-if="this.emailModal"
        v-bind:url="url"
        v-bind:type="type"
        v-bind:params="params"
        @closeEmailModal="this.emailModal=false"
        @succeedEmail="this.emailSuccess=true">
        </email-modal-component>

        <title-component>
            <template v-slot:emoji>😃</template>
            <template v-slot:title>내 정보 수정</template>
        </title-component>

        <div class="flex-container">
            <div class="inner">
                <div class="title" ref="profile-image">🤳🏻 프로필 이미지</div>
                <div class="sub-title">이미지로 나를 표현해 보세요.</div>
                <div class="preview-image" v-if="previewImage" :style="{'background-image': 'url(' + this.previewImage + ')' }"></div>
                <div class="no-image" v-if="!previewImage"><i class="fa-solid fa-user"></i></div>
                <input type="file" accept="image/*" class="profile-image" ref="uploadImage" @change="setPreviewImage">
            </div>

            <div class="inner">
                <div class="title" ref="nickname">💛 닉네임</div>
                <div class="sub-title">영문, 한글을 사용해 2-8자 사이의 닉네임을 만들어주세요.</div>
                <div class="input-box"
                    v-bind:class="{ 'checked': checkedNickname && this.nickname, 'unchecked': !checkedNickname && this.nickname }">
                    <input type="text" v-model="nickname" maxlength="8" @input="this.inputNickname($event)">
                    <i class="fa-solid fa-circle-check" v-if="checkedNickname && this.nickname"></i>
                    <i class="fa-solid fa-circle-xmark" v-if="!checkedNickname && this.nickname"></i>
                </div>
            </div>

            <div class="inner" ref="email">
                <div class="title">📧 이메일</div>
                <div class="sub-title">비밀번호를 찾을 때 사용할 이메일을 입력해주세요.
                    <span class="email-button" v-if="this.checkedEmail && !this.emailSuccess && this.email!=this.originalEmail" @click="this.clickEmailButton()">인증받기</span>
                </div>
                <div class="input-box"
                    v-bind:class="{ 'checked': checkedEmail && this.email, 'unchecked': !checkedEmail && this.email }">
                    <input type="text" v-model="email" maxlength="40" :readonly="this.emailSuccess">
                    <i class="fa-solid fa-circle-check" v-if="checkedEmail && this.email"></i>
                    <i class="fa-solid fa-circle-xmark" v-if="!checkedEmail && this.email"></i>
                </div>
            </div>

            <div class="inner" ref="gender">
                <div class="title">⚧️ 성별</div>
                <div class="sub-title">성별을 선택해주세요.</div>
                <div class="select-box">
                    <div class="select-item" v-bind:class="{ 'selected' : this.gender=='M' }"
                    @click="this.selectGender('M')">
                        <div class="emoji">👨🏻</div>
                        <div class="label">남성</div>
                    </div>
                    <div class="select-item" v-bind:class="{ 'selected' : this.gender=='F' }"
                    @click="this.selectGender('F')">
                        <div class="emoji">👩🏻</div>
                        <div class="label">여성</div>
                    </div>
                    <div class="select-item" v-bind:class="{ 'selected' : this.gender==null }"
                    @click="this.selectGender(null)">
                        <div class="emoji">🧑🏻‍🦲</div>
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
                <div class="medium-button" @click="modify()">
                    수정하기
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
            previewImage: this.$store.state.auth.user.profileImage,
            originalImage: this.$store.state.auth.user.profileImage,
            originalEmail: this.$store.state.auth.user.email,
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
            //  이메일 인증 모달
            emailModal: false,
            emailSuccess: false,
            url: "/email/auth/code",
            params: null,
            type: "E",
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
        //  닉네임 입력 감지
        inputNickname(event) {
            this.nickname = event.target.value;
        },

        //  프사 업로드
        setPreviewImage() {
            // 사진이 있을 시 사진 주소, 없을 시 null
            if(this.$refs.uploadImage.value!='') {
                // 파일용량 체크
                var size = this.$refs.uploadImage.files[0].size;
                var name = this.$refs.uploadImage.files[0].name;
                if (size > 2000000) {
                    this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"7MB를 넘는 이미지는 사용할 수 없어요." });
                    // 파일 입력 초기화
                    this.$refs.uploadImage.value = '';
                    return;
                }
                if(name.length > 100) {
                    this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"파일을 사용할 수 없어요." });
                    this.$refs.uploadImage.value = '';
                    return;
                }
                const url = URL.createObjectURL(this.$refs.uploadImage.files[0]);
                this.previewImage = url;
            } else {
                this.previewImage = null;
            }
        },

        //  아이디 중복 여부 체크
        checkNickname() {
            const params = {nickname : this.nickname};
            this.axios.get("/user/check", {params})
                .then((response) => {
                    this.checkedNickname = response.data;
                })
        },
        //  이메일 중복 여부 체크
        checkEmail() {
            const params = {email : this.email};
            this.axios.get("/user/check", {params})
                .then((response) => {
                    this.checkedEmail = response.data;
                })
        },
        
        //  성별 선택
        selectGender(gender) {
            this.gender = gender;
        },

        //  내 정보 수정
        modify() {
            // 입력 여부 확인
            if (!(this.checkedNickname || this.nickname.length==0)) {
                this.$refs.nickname.scrollIntoView({ behavior: "smooth" });
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"닉네임을 확인해주세요." });
            } else if (!this.checkedEmail) {
                this.$refs.email.scrollIntoView({ behavior: "smooth" });
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"이메일을 확인해주세요." });
            } else if (this.email!=this.$store.state.auth.user.email && !this.emailSuccess) {
                this.$refs.email.scrollIntoView({ behavior: "smooth" });
                this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"이메일 인증을 완료해주세요." });
            // 입력시 데이터 전송
            } else {
                const formData = new FormData();
                formData.append("email", this.email);
                if(this.nickname.length!=0) {
                    formData.append("nickname", this.nickname);
                }
                if(this.gender) {
                    formData.append("gender", this.gender);
                }
                if(this.birthday) {
                    formData.append("birthday", this.birthday);
                }
                if(this.originalImage) {
                    formData.append("originalImage", this.originalImage);
                }
                if(this.previewImage) {
                    formData.append("newImage", this.previewImage);
                }
                if(this.$refs.uploadImage.files[0]) {
                    formData.append("uploadFile", this.$refs.uploadImage.files[0]);
                }
                
                this.axios.put("/user/"+this.$store.state.auth.user.id, formData, {
                        headers: {
                            "Content-Type": "multipart/form-data",
                        },
                    }).then(() => {
                        this.$store.commit("modal/setAlert", { alertEmoji:"😀", alertText:"내 정보를 수정했어요." });
                        this.$router.push({ path: '/mypage' });
                    }).catch(()=>{
                        this.$store.commit("modal/setAlert", { alertEmoji:"⚠️", alertText:"다시 시도해주세요." });
                    });
            }
        },
        // 인증하기 버튼 클릭
        clickEmailButton() {
            this.params = {
                to: this.email.trim(),
            }
            this.emailModal = true;
        }
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
.inner {
    position: relative;
}

.preview-image, .profile-image, .no-image {
    width: 180px;
    height: 180px;
    background-size: cover;
    background-position: center;
    border-radius: 50%;
    position: relative;
    cursor: pointer;
}
.preview-image, .no-image {
    margin: 20px 0 0;
    display: flex;
    align-items: center;
    justify-content: center;
}
.profile-image {
    opacity: 0;
    position: absolute;
    bottom: 0;
    left: 0;
    width: 180px;
    height: 180px;
}
.fa-solid.fa-user {
    font-size: 96px;
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
.email-button {
    cursor: pointer;
    color: var(--FOCUS);
    font-weight: 600;
}
</style>