
const movie = {
    namespaced: true,
    state: {
        commentFlag: false, // 코멘트 입력 감지
    },
    mutations: {
        setCommentFlag(state, payload) {
            state.commentFlag = payload;
        }
    },
};

export default movie;