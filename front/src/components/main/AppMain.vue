fr<template>
    <div class="container">
        <router-link to="/">
            <app-title></app-title>
        </router-link>
        <div class="category">
            <router-link class="category-item" to="/search">영화 검색</router-link>
            <router-link class="category-item" to="/admin">관리자</router-link>
        </div>
        <router-view></router-view>
     </div>
 </template>
 
 <script>
 import AppTitle from '../common/AppTitle.vue'
 export default {
    components: {
        AppTitle
    },
 }
 </script>
 
 <style scoped>
    .category {
        display: flex;
        justify-content: center;
        gap: 20px;
    }
    .category-item {
        width: 120px;
        height: 40px;
        line-height: 40px;
        border-radius: 12px;
        background: var(--G50);
        color: var(--G1000);
        text-align: center;
        font-size: 16px;
    }
 </style>