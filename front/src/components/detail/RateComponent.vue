<template>
    <div class="user-area">
        <div class="option">
            <div class="item">
                <div class="star">
                    <div class="emoji rate-5" value="5" @click="rate">⭐️</div>
                    <div class="emoji rate-4" value="4" @click="rate">⭐️</div>
                    <div class="emoji rate-3" value="3" @click="rate">⭐️</div>
                    <div class="emoji rate-2" value="2" @click="rate">⭐️</div>
                    <div class="emoji rate-1" value="1" @click="rate">⭐️</div>
                </div>
                <div class="text">평가하기</div>
            </div>
        </div>
        <div class="option">
            <div class="item" ref="comment" @click="comment">
                <div class="emoji">📝</div>
                <div class="text">코멘트</div>
            </div>
            <div class="item" ref="bookmark" @click="bookmark">
                <div class="emoji">🙏</div>
                <div class="text">보고싶어요</div>
            </div>
            <div class="item" ref="watching" @click="watching">
                <div class="emoji">😎</div>
                <div class="text">보는 중</div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    methods: {
        // 별점 클릭
        rate(event) {
            const target = event.target.getAttribute("value");
            const nodes = document.querySelectorAll(".star > .emoji");

            for(let i=4 ; i>=0 ; i--) {
                if(5-i<=target) {
                    nodes[i].classList.add("selected");
                } else {
                    nodes[i].classList.remove("selected");
                }
            }
        },
        comment() {
            const parent = this.$refs.comment;
            const emoji = parent.firstChild;
            emoji.classList.toggle("selected");
        },
        bookmark() {
            const parent = this.$refs.bookmark;
            const emoji = parent.firstChild;
            emoji.classList.toggle("selected");
        },
        watching() {
            const parent = this.$refs.watching;
            const emoji = parent.firstChild;
            emoji.classList.toggle("selected");
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