<template>
<div id="footer">
    <div class="count">
        <div class="container">
            <div class="summary">지금까지 ⭐️<span class="accent">{{ count }}개의 코멘트</span>가 쌓였어요.</div>
        </div>
    </div>
    <div class="information">
        <div class="container">
            <div class="item">
                <div class="subject">고객센터</div>
                <div class="section">|</div>
                <div>mrge.tistory.com</div>
            </div>
            <div class="item">
                <div class="subject">광고문의</div>
                <div class="section">|</div>
                <div>github.com/szzzing ・ 02-1234-1234</div>
            </div>
            <div class="item">
                <div class="subject">제휴 및 대외 협력</div>
                <div class="section">|</div>
                <div>xxszzzing@gmail.com</div>
            </div>
        </div>
        <div class="container copyright">
            <div><b>Reminiscene🌙</b> ⓒ 2023 by szzzing, All rights reserved</div>
            <router-link v-if="this.$store.state.auth.user" to="/mypage/withdraw" class="withdraw">회원탈퇴</router-link>
        </div>
    </div>
</div>
</template>

<script>
export default {
    created() {
        this.fetchData();
    },
    data() {
        return {
            count: null,
        }
    },
    methods: {
        fetchData() {
            this.axios.get("/comment/count")
            .then((response)=>{
                this.count = response.data;
            })
        }
    },
    watch: {
        '$route.path': 'fetchData',
    },
}
</script>

<style scoped>
#footer {
    /* margin-top: 100px; */
}
* {
    color: var(--G400);
}
.count {
    background: var(--G900);
    padding: 16px 0;
    text-align: center;
}
.dark .count {
    background: var(--G1000);
}
.information {
    background: var(--G800);
    padding: 20px 0 48px;
    display: flex;
    flex-direction: column;
    gap: 20px;
}
.summary {
    font-size: 18px;
    font-weight: 500;
    color: var(--G200);
    letter-spacing: 0.2px;
}
.accent {
    font-weight: 600;
    font-size: 18px;
    color: var(--FOCUS);
}
.copyright {
    display: flex;
    justify-content: space-between;
}
.copyright, .copyright div, .copyright b {
    color: var(--G500);
    font-size: 12px;
}
.item {
    display: flex;
    gap: 8px;
    font-size: 12px;
}
</style>