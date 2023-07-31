<template>
    <div class="wrap">
      <app-header></app-header>
      <div class="flex-wrap">
        <router-view></router-view>
        <app-footer></app-footer>
      </div>
    </div>
</template>

<script>
import AppHeader from './components/common/AppHeader.vue'
import AppFooter from './components/common/AppFooter.vue'
export default {
  components: {
    AppHeader,
    AppFooter,
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
}
body {
  background: #181818;
  color: #fff;
  margin: 0;
  padding: 0;
  height: 100%;
}
.flex-wrap {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-height: 100vh;
}
.container {
    width: calc(100% - 80px);
    margin: 0 auto;
    padding: 60px 0;
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
</style>