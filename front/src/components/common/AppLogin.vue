<template>
    <div class="container">
        <div class="title-area">
            <div class="title">
                🌙
            </div>
            <div class="sub-title">어떤 영화가 궁금해?</div>
        </div>
        <div class="title">로그인</div>
        <div class="input-area">
            <div class="input-box">
                <input type="text" placeholder="아이디" v-model="id">
                <input type="password" placeholder="비밀번호" v-model="pw">
            </div>
            <div class="result" v-if="this.$store.state.query!=''">
                <b>{{ this.$store.state.query }}</b>에 대한 영화를 보여줄게.
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            id: '',
            pw: '',
        }
    },
    watch: {
        query() {
            this.$store.commit('setQuery', this.query);
            this.$store.dispatch('searchList');
        }
    }
}
</script>

<style scoped>
    .container {
        max-width: 1280px;
    }
    div {
        text-align: center;
    }
    .title-area {
        margin-bottom: 100px;
    }
    .title {
        margin: 0 0 12px;
        font-size: 40px;
        text-align: center;
        color: #fff;
    }
    .sub-title {
        color: #aaa;
        text-align: center;
        font-size: 18px;
        font-weight: 700;
    }

    .input-area {
        margin-top: 60px;
    }
    input {
        border: none;
        width: 100%;
        background: #f7f7f7;
        font-size: 20px;
    }
    input::placeholder {
        color: #ddd;
    }
    .input-box {
        background: #f9f9f9;
        max-width: 200px;
        line-height: 36px;
        border-radius: 16px;
        padding: 12px 24px;
        margin: 0 auto;
    }
    :focus {
        outline: none;
    }
    .result {
        font-size: 24px;
        margin: 40px 0 60px;
        color: #fff;
    }
</style>