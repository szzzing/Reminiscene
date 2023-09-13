<template>
<div id="footer">
    <div class="container">
        <div class="inner">
            <div class="count">지금까지 <span class="accent">{{ count }}개의 코멘트</span>가 쌓였어요.</div>
            <div class="copyright">copyright ⓒ szzzing🌙 All Rights Reserved</div>
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
    padding: 24px 0 72px;
    background: var(--G1000);
}
.container {
    display: flex;
    flex-direction: column;
    gap: 24px;
}
* {
    color: var(--G300);
    font-size: 14px;
}
.inner {
    text-align: center;
}
.count {
    font-size: 18px;
    font-weight: 600;
}
.copyright {
    color: var(--G400);
}
.accent {
    font-weight: 700;
    font-size: 18px;
    color: var(--FOCUS);
}
.item {
    display: flex;
    gap: 8px;
}
</style>