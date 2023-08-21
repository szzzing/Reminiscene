<template>
    <div class="user-area">
        <div class="option">
            <div class="item">
                <div class="star">
                    <div class="emoji rate-5" value="5" @click="this.clickRate(5)">⭐️</div>
                    <div class="emoji rate-4" value="4" @click="this.clickRate(4)">⭐️</div>
                    <div class="emoji rate-3" value="3" @click="this.clickRate(3)">⭐️</div>
                    <div class="emoji rate-2" value="2" @click="this.clickRate(2)">⭐️</div>
                    <div class="emoji rate-1" value="1" @click="this.clickRate(1)">⭐️</div>
                </div>
                <div class="text">{{ this.rateText }}</div>
            </div>
        </div>
        <div class="option">
            <div class="item" ref="comment" @click="this.isComment = !this.isComment">
                <div class="emoji" :class="{'selected' : isComment}">📝</div>
                <div class="text">코멘트</div>
            </div>
            <div class="item" ref="wish" @click="this.clickWish()">
                <div class="emoji" :class="{'selected' : isWish}">🙏</div>
                <div class="text">보고싶어요</div>
            </div>
            <div class="item" ref="watching" @click="this.clickWatching()">
                <div class="emoji" :class="{'selected' : isWatching}">😎</div>
                <div class="text">보는 중</div>
            </div>
        </div>
    </div>
</template>

<script>
let movieId;

export default {
    // 유저 상태 정보 받아오기
    async created() {
        if(this.$store.state.auth.user) {

            movieId = this.$route.params.id;

            try {
                const response = await this.axios.get("/movie/status/"+movieId);
                this.rate = response.data.rate;
                this.isComment = response.data.comment;
                this.isWatching = response.data.watching;
                this.isWish = response.data.wish;
            } catch(error) {
                this.$router.push('/error');
            }
        }
    },
    data() {
        return {
            rate: 0,
            isComment: false,
            isWatching: false,
            isWish: false,
            rateText: '평가하기',
        }
    },
    props: [
       'movie',
    ],
    watch: {
        rate() {
            const nodes = document.querySelectorAll(".star > .emoji");
            for(let i=4 ; i>=0 ; i--) {
                if(5-i<=this.rate) {
                    nodes[i].classList.add("selected");
                } else {
                    nodes[i].classList.remove("selected");
                }
            }
            if(this.rate==5) this.rateText="최고예요";
            if(this.rate==4) this.rateText="재미있어요";
            if(this.rate==3) this.rateText="볼만해요";
            if(this.rate==2) this.rateText="그저그래요";
            if(this.rate==1) this.rateText="별로예요";
            if(this.rate==0) this.rateText="평가하기";
        },
        isComment() {
            const parent = this.$refs.comment;
            const emoji = parent.firstChild;
            emoji.classList.toggle("selected");
        },
        isWish() {
            const parent = this.$refs.wish;
            const emoji = parent.firstChild;
            emoji.classList.toggle("selected");
        },
        isWatching() {
            const parent = this.$refs.watching;
            const emoji = parent.firstChild;
            emoji.classList.toggle("selected");
        },
    },
    // 클릭 이벤트
    methods: {
        // 별점 클릭
        clickRate(star) {
            // 별점 삽입
            if(!this.rate) {
                this.axios.post("/movie/rate/"+movieId, {star})
                .then(()=>{
                    this.rate = star;
                    this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"에 "+star+"점을 주었어요." });
                });
            }
            // 별점 수정
            else if(this.rate && this.rate!=star) {
                this.axios.put("/movie/rate/"+movieId, {star})
                .then(()=>{
                    this.rate = star;
                    this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"에 "+star+"점을 주었어요." });
                });
            }
            // 별점 삭제
            else {
                this.axios.delete("/movie/rate/"+movieId)
                .then(()=>{
                    this.rate = 0;
                    this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"의 별점을 삭제했어요." });
                });
            }
        },

        // 보고싶어요 클릭
        clickWish() {
            // 삭제
            if(this.isWish) {
                this.axios.delete("/movie/wish/"+movieId)
                .then(()=>{
                    this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"를 위시리스트에서 삭제했어요." });
                });
            }
            // 삽입
            else {
                this.axios.post("/movie/wish/"+movieId)
                .then(()=>{
                    this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"를 위시리스트에 추가했어요." });
                });
            }
            this.isWish = !this.isWish;
        },

        // 보는중 클릭
        clickWatching() {
            // 삭제
            if(this.isWatching) {
                this.axios.delete("/movie/watching/"+movieId)
                .then(()=>{
                    this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"를 그만볼래요." });
                });
            }
            // 삽입
            else {
                this.axios.post("/movie/watching/"+movieId)
                .then(()=>{
                    this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"를 보고있어요." });
                });
            }
            this.isWatching = !this.isWatching;
        }
    },
}
</script>

<style scoped>
.star > .emoji:hover, .star > .emoji:hover ~ .emoji {
    filter: none;
    opacity: 1;
}
.star > .emoji:hover {
    transform: scale(1.2);
}

.user-area {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    gap: 20px 0;
}
.option {
    display: flex;
    gap: 16px;
    padding-bottom: 16px;
    border-bottom: 1px solid var(--G200);
    justify-content: space-between;
}
.item {
    display: flex;
    flex-direction: column;
    cursor: pointer;
}
.option:first-child {
    flex-grow: 1;
}
.emoji {
    line-height: 60px;
    font-size: 36px;
    text-align: center;
    color: var(--G200);
    filter: grayscale(100%);
    opacity: 0.5;
    transition: all 0.2s ease;
}
.item:hover > .emoji {
    transform: scale(1.2);
    filter: grayscale(0);
    opacity: 1;
}
.text {
    text-align: center;
    color: var(--G500);
}
.star {
    display: flex;
    flex-direction: row-reverse;
    justify-content: center;
}
@media screen and (max-width:600px) {
    .user-area {
        flex-direction: column;
    }
    .option:first-child {
        justify-content: center;
    }
}
.selected {
    filter: grayscale(0%);
    opacity: 1;
    animation: scale 0.5s alternate;
}
</style>