<template>
    <transition name="smooth">
        <div id="email-modal" class="modal-mask" @click="$emit('closeEmailModal')">
            <div class="modal-wrapper">
                <div class="modal-container item-fill" @click.stop="">
                    <div class="inner">
                        <div class="emoji shadow">🧙</div>
                        <div class="sub-title">{{ this.email+"로 인증코드를 보냈어요.\n6자리 인증코드를 입력해주세요." }}</div>
                    </div>
                    <div class="inner">
                        <div class="input-box">
                            <input class="code" type="text" size="6" v-model="input" @input="this.checkInput()" maxlength="6">
                        </div>
                    </div>
                    <div class="inner">
                        <div class="text">메일이 안왔나요?</div>
                        <span class="send-button" @click="this.sendEmail()">다시 보내기</span>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>

<script>

export default {
    data() {
        return {
            input: null,
            code: null,
        }
    },
    props: [
        'email',
    ],
    created() {
        this.sendEmail();
    },
    methods: {
        // 이메일 전송
        sendEmail() {
            const email = this.email.trim();

            this.axios.post("/email", email)
            .then((response)=>{
                this.code = response.data;
            })
        },
        checkInput() {
            this.input = this.input.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
        }
    },
    watch: {
        input() {
            if(this.input == this.code) {
                this.$emit('closeEmailModal');
                this.$emit('succeedEmail');
                this.$store.commit("modal/setAlert", { alertEmoji:"📧", alertText:"이메일 인증에 성공했어요." });
            }
        }
    }
}
</script>

<style scoped>
.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: table;
}
.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}
.modal-container {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    border-radius: 8px;;
    display: flex;
    flex-direction: column;
    gap: 16px;
}
.inner {
    display: flex;
}
.inner:first-child {
    flex-direction: column;
}
.inner:nth-child(2) {
    gap: 6px;
    flex-grow: 1;
    justify-content: center;
}
.inner:last-child {
    justify-content: center;
    gap: 8px;
}
.emoji {
    text-align: center;
    font-size: 48px;
    line-height: 1.2;
}
.title {
    flex-grow: 1;
    font-size: 20px;
    font-weight: 700;
    text-align: center;
}
.sub-title {
    text-align: center;
    color: var(--G500);
}
.close {
    cursor: pointer;
}
.medium-button {
    width: 80px;
    margin-left: auto;
}
.code {
    font-size: 24px;
    line-height: 60px;
    letter-spacing: 10px;
    text-align: center;
    width: 180px;
}
.input-box {
    padding: 0;
}
.dark .input-box {
    background: var(--G100);
}
.text {
    color: var(--G400);
}
.send-button {
    color: var(--FOCUS);
    font-weight: 500;
    cursor: pointer;
}
</style>