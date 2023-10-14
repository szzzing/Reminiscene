<template>
    <transition name="smooth" v-if="this.succeed">
        <div id="email-modal" class="modal-mask" @click="$emit('closeEmailModal')">
            <div class="modal-wrapper">
                <div class="modal-container item-fill" @click.stop="">
                    <div class="inner">
                        <div class="emoji shadow">{{ emoji }}</div>
                        <div class="sub-title" ref="status" v-if="this.status==null">{{ "인증번호를 입력해주세요." }}</div>
                        <div class="sub-title status-success" v-if="this.status==true" ref="status">이메일 인증에 성공했어요.</div>
                        <div class="sub-title status-fail" v-if="this.status==false" ref="status">인증번호를 다시 확인해주세요.</div>
                        <div class="timer"
                        :class="{ 'status-fail' : this.timer.expired }"
                        >{{ timer.text }}</div>
                    </div>
                    <div class="inner input-area" ref="code">
                        <input class="code" type="text" v-model="code[0]" size="1" @input="this.checkcode($event,0)" maxlength="1">
                        <input class="code" type="text" v-model="code[1]" size="1" @input="this.checkcode($event,1)" maxlength="1">
                        <input class="code" type="text" v-model="code[2]" size="1" @input="this.checkcode($event,2)" maxlength="1">
                        <input class="code" type="text" v-model="code[3]" size="1" @input="this.checkcode($event,3)" maxlength="1">
                        <input class="code" type="text" v-model="code[4]" size="1" @input="this.checkcode($event,4)" maxlength="1">
                        <input class="code" type="text" v-model="code[5]" size="1" @input="this.checkcode($event,5)" maxlength="1">
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
            status: null,
            emoji: "😐",
            succeed: false,
            timer: {
                time: 180,
                text: "3 : 00",
                expired: false,
            },
            interval: null,
            code: [null, null, null, null, null, null],  // 입력값
        }
    },
    props: [
        'url',
        'params',
    ],
    created() {
        this.sendEmail();
    },
    methods: {
        // 이메일 전송
        sendEmail() {
            this.axios.post(this.url, this.params)
            .then((response)=>{
                if(response.data) {
                    this.startTimer();
                    this.succeed = true;
                }
            })
        },
        checkcode(event, index) {
            this.code[index] = event.target.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');
            if(this.code[index].length>0 && index<5) {
                event.target.nextSibling.focus();
            }
            let value = '';
            for(const n of this.code) {
                if(n) value = value+n;
            }
            if(value.length==6) {
                this.match(value);
            }
        },
        match(value) {
            const params = {
                email: this.params.to,
                code: value,
            };
            this.axios.get("/email/match", {params})
            .then((response)=>{
                if(response.data) {
                    this.status = true;
                    this.emoji = "😀";
                    setTimeout(() => {
                        this.$emit('closeEmailModal');
                        this.$emit('succeedEmail');
                    }, 1000);
                } else {
                    this.$refs.code.classList.add("vibration");
                    this.status = false;
                    this.emoji = "😢";
                    setTimeout(() => {
                        this.code = [null, null, null, null, null, null];
                        document.getElementsByClassName("code")[0].focus();
                        this.$refs.code.classList.remove("vibration");
                        this.status = null;
                        this.emoji = "😐";
                    }, 1000);
                }
                clearTimeout();
            });
        },
        startTimer() {
            this.timer.expired = false;
            this.timer.text = "3분 0초 남았어요.";
            this.timer.time = 179;
            clearInterval(this.interval);

            this.interval = setInterval(()=>{
                const min = parseInt(this.timer.time/60);
                const sec = String(this.timer.time%60);
                this.timer.text = `${min}분 ${sec}초 남았어요.`;
                this.timer.time--;

                if(this.timer.time<0) {
                    this.timer.expired = true;
                    this.timer.text = "인증 가능한 시간이 지났어요.";
                    clearInterval(this.interval);
                }
            }, 1000);
        },
    },
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
    gap: 4px;
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
}
.title {
    flex-grow: 1;
    font-size: 20px;
    font-weight: 700;
    text-align: center;
}
.sub-title, .timer {
    text-align: center;
    word-break: keep-all;
    font-weight: 600;
    line-height: 1.4;
}
.timer {
    color: var(--G500);
}
.code {
    font-size: 24px;
    width: 44px;
    border-radius: 8px;
    text-align: center;
    padding: 4px 0;
    background: var(--G50);
    border: 1px solid var(--G100);
}
.text {
    color: var(--G500);
}
.send-button {
    color: var(--FOCUS);
    font-weight: 500;
    cursor: pointer;
}
.status-success {
    color: var(--GREEN);
}
.status-fail {
    color: var(--RED);
}
</style>