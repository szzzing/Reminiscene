<template>
    <div class="container">
        <router-view v-bind:user="user"></router-view>
    </div>
</template>

<script>
import moment from "moment";

export default {

    created() {
        this.fetchUser();
    },
    data() {
        return {
            user: null,
        }
    },
    methods: {
        fetchUser() {
            this.axios.get("/user/"+this.$route.params.id)
            .then((response)=>{
                this.user = response.data;
                this.user.birthday = moment(this.user.birthday).format();
            })
            .catch(()=>{
                this.$router.push('/error');
            });
            console.log("fetch")
        }
    },
    watch: {
        '$route.params.id': 'fetchUser',
    }
}
</script>

<style scoped>
.container {
    max-width: 960px;
    display: flex;
    flex-direction: column;
    gap: 72px;
}
</style>