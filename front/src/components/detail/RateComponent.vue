<template>
    <div class="user-area">
        <div class="option">
            <div class="item">
                <div class="star">
                    <div class="emoji rate-5" value="5" @click="this.rating(5)">⭐️</div>
                    <div class="emoji rate-4" value="4" @click="this.rating(4)">⭐️</div>
                    <div class="emoji rate-3" value="3" @click="this.rating(3)">⭐️</div>
                    <div class="emoji rate-2" value="2" @click="this.rating(2)">⭐️</div>
                    <div class="emoji rate-1" value="1" @click="this.rating(1)">⭐️</div>
                </div>
                <div class="text">평가하기</div>
            </div>
        </div>
        <div class="option">
            <div class="item" ref="comment" @click="this.isComment = !this.isComment">
                <div class="emoji" :class="{'selected' : isComment}">📝</div>
                <div class="text">코멘트</div>
            </div>
            <div class="item" ref="bookmark" @click="this.isBookmark = !this.isBookmark">
                <div class="emoji" :class="{'selected' : isBookmark}">🙏</div>
                <div class="text">보고싶어요</div>
            </div>
            <div class="item" ref="watching" @click="this.isWatching = !this.isWatching">
                <div class="emoji" :class="{'selected' : isWatching}">😎</div>
                <div class="text">보는 중</div>
            </div>
        </div>
    </div>
</template>

<script>
let userId;
let movieId;

export default {
    // 유저 상태 정보 받아오기
    async created() {
        userId = this.$store.state.auth.user.id;
        movieId = this.$route.params.id;

        if(this.$store.state.auth.user) {

            try {
                const response = await this.axios.get("/movie/"+movieId+"/status/"+userId);
                this.rate = response.data.rate;
                this.isComment = response.data.comment;
                this.isWatching = response.data.watching;
                this.isBookmark = response.data.bookmark;
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
            isBookmark: false,
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
        },
        isComment() {
            const parent = this.$refs.comment;
            const emoji = parent.firstChild;
            emoji.classList.toggle("selected");
        },
        isBookmark() {
            const parent = this.$refs.bookmark;
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
        rating(star) {
            // 별점 삽입
            if(!this.rate) {
                this.axios.post("/movie/rate", {userId, movieId, star})
                .then(()=>{
                    this.rate = star;
                    this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"에 "+star+"점을 주었어요." });
                });
            }
            // 별점 수정
            else if(this.rate && this.rate!=star) {
                this.axios.put("/movie/"+movieId+"/rate/"+userId, {star})
                .then(()=>{
                    this.rate = star;
                    this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"에 "+star+"점을 주었어요." });
                });
            }
            // 별점 삭제
            else {
                this.axios.delete("/movie/"+movieId+"/rate/"+userId)
                .then(()=>{
                    this.rate = 0;
                    this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"의 별점을 삭제했어요." });
                });
            }
        },
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