<template>
    <div class="container">
    </div>
</template>
 
<script>
export default {
    created() {
        this.axios.get("/movies")
        .then((response)=>{
            console.log(response.data);
        })
    }
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