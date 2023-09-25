<template>
    <div id="title">
        <div class="emoji shadow">
            <slot name="emoji"></slot>
        </div>
        <div class="title">
            <slot name="title"></slot>
        </div>
    </div>
</template>

<script>
export default {
}
</script>

<style scoped>
    #title {
        margin: 0 0 36px;
    }
    .emoji {
        font-size: 40px;
    }
    .title {
        font-size: 28px;
        font-weight: 600;
        line-height: 1.4;
    }
</style>