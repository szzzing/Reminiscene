<template>
    <div class="wrap" v-bind:class="{ dark : !this.$store.state.auth.theme }">
      <header-item></header-item>
      <div class="flex-wrap">
        <router-view></router-view>
      </div>
    </div>
</template>

<script>
import HeaderItem from './components/common/HeaderItem.vue'

export default {
  components: {
    HeaderItem,
  },
  data() {
    return {
      movieList: {}
    }
  },
  methods: {
    getList(list) {
      this.movieList = list;
    }
  }
}
</script>

<style>
@import url('https://cdn.jsdelivr.net/gh/toss/tossface/dist/tossface.css');
@import url('https://static.toss.im/tps/main.css');
@import url('https://static.toss.im/tps/others.css');

* {
  padding: 0;
  margin: 0;
  font-family: 'Toss Product Sans', TossFace;
  list-style: none;
  color: var(--G1000);
  box-sizing: border-box;
}
body {
  margin: 0;
  padding: 0;
  height: 100%;
}
.wrap {
  background: var(--G0);
}
.flex-wrap {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-height: 100vh;
}
.flex-wrap > .container:first-child {
  min-height: calc(100vh);
  padding: 100px 0 100px;
}
.container {
    width: calc(100% - 80px);
    margin: 0 auto;
}
@media screen and (max-width:768px) {
    .container {
        width: calc(100% - 40px);
        margin: 0 auto;
    }
}

a {
  text-decoration: none;
  color: unset;
}
input {
  border: none;
  width: 100%;
  background: transparent;
}
input::placeholder {
  color: var(--G300);
}
.input-box {
    background: var(--G50);
    height: 60px;
    line-height: 60px;
    border-radius: 16px;
    padding: 0 24px;
    margin: 0 auto;
}
::-webkit-scrollbar {
  width: 0px;
	height: 0;
}
::-webkit-scrollbar-track {
  display: none;
}
::-webkit-scrollbar-thumb {
  background: var(--G50);
  border-radius: 5px;
}
:focus {
	outline: 0;
}

.emoji {
  filter: drop-shadow(0px 0px 6px var(--O200));
}

.big-button {
  font-size: 18px;
  font-weight: 500;
  max-width: 240px;
  text-align: center;
  color: var(--G0);
  background: var(--G1000);
  height: 48px;
  line-height: 48px;
  border-radius: 16px;
  cursor: pointer;
}

.medium-button {
  font-size: 16px;
  font-weight: 500;
  max-width: 240px;
  text-align: center;
  color: var(--G0);
  background: var(--G1000);
  height: 36px;
  line-height: 36px;
  border-radius: 16px;
  cursor: pointer;
}


:root {
	--G0: #ffffff;
	--G50: #f9fafb;
	--G100: #f7f9fb;
	--G200: #eceef1;
	--G300: #d1d6db;
	--G400: #b0b8c1;
	--G500: #8b95a1;
	--G600: #6b7684;
	--G700: #333;
	--G800: #222;
	--G900: #181818;
	--G1000: #111;
}
.dark,
.dark * {
	--G1000: #ffffff;
	--G900: #f7f9fb;
	--G800: #ccc;
	--G700: #bbb;
	--G600: #a8a8a8;
	--G500: #999;
	--G400: #888;
	--G300: #666;
	--G200: #333;
	--G100: #222;
	--G50: #181818;
	--G0: #111;
}
:root {
  --O100: rgba(2, 32, 71, 0.05);
	--O200: rgba(0, 27, 55, 0.08);
	--O300: rgba(0, 29, 58, 0.18);
	--O400: rgba(0, 29, 54, 0.31);
	--O500: rgba(3, 24, 50, 0.46);
	--O600: rgba(0, 19, 43, 0.58);
	--O700: rgba(3, 18, 40, 0.7);
	--O800: rgba(0, 12, 30, 0.8);
	--O900: rgba(2, 9, 19, 0.91);
  --FOCUS: #ffc400;
}
</style>