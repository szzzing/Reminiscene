<template>
    <comment-modal-component v-bind:movie="movie" v-if="this.commentModal"
    @closeCommentModal="this.commentModal=false"
    @changeCommentStatus="this.changeCommentStatus"
    v-bind:isComment="isComment"
    v-bind:commentContent="commentContent"
    v-bind:commentIsSpoiler="commentIsSpoiler">
    </comment-modal-component>

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
            <div class="item" ref="comment" @click="this.clickComment()">
                <div class="emoji" :class="{'selected' : isComment && this.$store.state.auth.user}">📝</div>
                <div class="text">코멘트</div>
            </div>
            <div class="item" ref="wish" @click="this.clickWish()">
                <div class="emoji" :class="{'selected' : isWish && this.$store.state.auth.user}">🙏</div>
                <div class="text">보고싶어요</div>
            </div>
            <div class="item" ref="watching" @click="this.clickWatching()">
                <div class="emoji" :class="{'selected' : isWatching && this.$store.state.auth.user}">😎</div>
                <div class="text">보는 중</div>
            </div>
        </div>
    </div>
</template>

<script>
import CommentModalComponent from './CommentModalComponent.vue';

export default {
    components: {
        CommentModalComponent,
    },
    // 유저 상태 정보 받아오기
    async created() {
        this.fetchData();
    },
    data() {
        return {
            commentModal: false,
            rate: 0,
            isComment: false,
            isWatching: false,
            isWish: false,
            commentContent: null,
            commentIsSpoiler: false,
            rateText: '평가하기',
        }
    },
    props: [
       'movie',
    ],
    computed: {
        checkUser() {
            return this.$store.state.auth.user;
        },
        commentFlag() {
            return this.$store.state.movie.commentFlag;
        },
    },
    watch: {
        // 파라미터 변경
        '$route.params.id': 'fetchData',

        // 코멘트 작성/수정
        commentFlag() {
            console.log("패치");
            this.fetchData();
            this.$store.commit("movie/setCommentFlag", false);
        },

        // 로그아웃 감지
        checkUser() {
            if(!this.$store.state.auth.user) {
                this.rate = 0;
            }
        },
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
    methods: {
        fetchData() {
            if(this.$store.state.auth.user) {
                this.axios.get("/status/"+this.$route.params.id)
                .then((response)=>{
                    this.rate = response.data.rate;
                    this.isComment = response.data.comment;
                    this.isWatching = response.data.watching;
                    this.isWish = response.data.wish;
                    this.commentContent = response.data.commentContent;
                    this.commentIsSpoiler = response.data.commentIsSpoiler;
                });
            }
        },
        // 코멘트 작성/수정
        changeCommentStatus(content, spoiler) {
            this.isComment=true;
            this.commentContent = content;
            this.commentIsSpoiler = spoiler;
        },
        // 별점 클릭
        clickRate(star) {
            // 별점 삽입
            if(this.$store.state.auth.user) {
                const params = {
                    movieId: this.$route.params.id,
                    star: star,
                }
                if(!this.rate) {
                    this.axios.post("/rate", params)
                    .then(()=>{
                        this.rate = star;
                        this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"에 "+star+"점을 주었어요." });
                    });
                }
                // 별점 수정
                else if(this.rate && this.rate!=star) {
                    this.axios.put("/rate", params)
                    .then(()=>{
                        this.rate = star;
                        this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"에 "+star+"점을 주었어요." });
                    });
                }
                // 별점 삭제
                else {
                    this.axios.delete("/rate/"+this.$route.params.id)
                    .then(()=>{
                        this.rate = 0;
                        this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"의 별점을 삭제했어요." });
                    });
                }
            } else {
                this.$store.commit("modal/setAlert", { alertEmoji: "✋", alertText: "로그인 후 이용해주세요." });
            }
        },

        // 보고싶어요 클릭
        clickWish() {
            if(this.$store.state.auth.user) {
                // 삭제
                if(this.isWish) {
                    this.axios.delete("/wish/"+this.$route.params.id)
                    .then(()=>{
                        this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"를 보고싶지 않아요." });
                    });
                }
                // 삽입
                else {
                    this.axios.post("/wish", {movieId: this.$route.params.id})
                    .then(()=>{
                        this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"를 보고싶어요." });
                    });
                }
                this.isWish = !this.isWish;

            } else {
                this.$store.commit("modal/setAlert", { alertEmoji: "✋", alertText: "로그인 후 이용해주세요." });
            }
        },

        // 보는중 클릭
        clickWatching() {
            if(this.$store.state.auth.user) {
                // 삭제
                if(this.isWatching) {
                    this.axios.delete("/watching/"+this.$route.params.id)
                    .then(()=>{
                        this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"를 그만볼래요." });
                    });
                }
                // 삽입
                else {
                    this.axios.post("/watching", {movieId: this.$route.params.id})
                    .then(()=>{
                        this.$store.commit("modal/setAlert", { alertEmoji: "✨", alertText: this.movie.title+"를 보고있어요." });
                    });
                }
                this.isWatching = !this.isWatching;

            } else {
                this.$store.commit("modal/setAlert", { alertEmoji: "✋", alertText: "로그인 후 이용해주세요." });
            }
        },

        // 코멘트 클릭
        clickComment() {
            if(this.$store.state.auth.user) {
                this.commentModal = true;
            } else {
                this.$store.commit("modal/setAlert", { alertEmoji: "✋", alertText: "로그인 후 이용해주세요." });
            }
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
    .option:last-child {
        justify-content: space-around;
    }
}
.selected {
    filter: grayscale(0%);
    opacity: 1;
    animation: scale 0.5s alternate;
}
</style>