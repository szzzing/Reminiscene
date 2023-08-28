<template>
    <div id="stastics">
        <div class="avg-rate">
            <div class="emoji">⭐️</div>
            <div class="value">{{ this.avgRate }}</div>
            <div class="discription">{{ this.rateCount+"명의 평가" }}</div>
        </div>
        <div class="wish-count">
            <div class="emoji">🙏</div>
            <div class="value">{{  this.wishCount+"명" }}</div>
            <div class="discription">보고싶어요</div>
        </div>
        <div class="watcing-count">
            <div class="emoji">😎</div>
            <div class="value">{{ this.watchingCount+"명" }}</div>
            <div class="discription">보는중</div>
        </div>
    </div>
</template>

<script>
export default {
    created() {
        this.axios.get("/statistics/"+this.$route.params.id)
        .then((response)=>{
            this.avgRate = response.data.avgRate;
            this.rateCount = response.data.rateCount;
            this.wishCount = response.data.wishCount;
            this.watchingCount = response.data.watchingCount;
        })
    },
    data() {
        return {
            avgRate: null,
            rateCount: null,
            wishCount: null,
            watchingCount: null,
        }
    }
}
</script>

<style scoped>
#stastics {
    display: flex;
    justify-content: center;
    gap: 30px;
}
#stastics > * {
    display: flex;
    flex-direction: column;
    text-align: center;
    flex-shrink: 0;
}
.discription {
    color: var(--G500);
    line-height: 1.4;
}
.emoji {
    font-size: 36px;
    font-weight: 700;
    line-height: 1.4;
}
.value {
    font-size: 18px;
    line-height: 1.4;
    font-weight: 700;
}
</style>