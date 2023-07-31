fr<template>
    <div class="container">
        <router-link to="/">
            <div class="title">
                🌙
            </div>
            <div class="sub-title">어떤 영화가 궁금해?</div>
        </router-link>
        <div class="category">
            <router-link class="category-item" to="/join">회원가입</router-link>
            <router-link class="category-item" to="/login">로그인</router-link>
            <router-link class="category-item" to="/search">영화 검색</router-link>
            <router-link class="category-item" to="/admin">관리자</router-link>
        </div>
        <router-view></router-view>
     </div>
 </template>
 
 <script>
 export default {
    
 }
 </script>
 
 <style scoped>
     .title {
         margin: 0 0 12px;
         font-size: 40px;
         font-weight: 900;
         text-align: center;
         color: #fff;
     }
     .sub-title {
         color: #aaa;
         text-align: center;
         font-size: 18px;
         font-weight: 700;
     }
     .category {
        display: flex;
        justify-content: center;
        gap: 20px;
        max-width: 600px;
        margin: 60px auto;
     }
     .category-item {
         width: calc(100% / 4);
         height: 36px;
         line-height: 36px;
         border-radius: 12px;
         background: #262626;
         text-align: center;
         font-size: 16px;
         
     }
 </style>