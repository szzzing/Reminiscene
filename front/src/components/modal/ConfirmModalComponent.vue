<template>
    <transition name="smooth">
        <div class="modal-mask" @click="$emit('closeConfirmModal')">
            <div class="modal-wrapper">
                <div class="modal-container item-fill" @click.stop="">
                    <div class="inner">
                        <div class="emoji shadow"><slot name="emoji"></slot></div>
                        <div class="text"><slot name="text"></slot></div>
                    </div>
                    <div class="inner">
                        <div class="button" @click="$emit('yes')">네</div>
                        <div class="button" @click="$emit('no')">아니요</div>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>

<script>
export default {
}
</script>

<style scoped>
.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: table;
}
.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}
.modal-container {
    max-width: 300px;
    margin: 0 auto;
    border-radius: 8px;;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    gap: 16px;
    text-align: center;
}
.inner {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
}
.inner:first-child {
    flex-direction: column;
    padding: 20px 20px 0;
}
.inner:last-child {
    border-top: 1px solid var(--G100);
}
.emoji {
    font-size: 36px;
}
.button {
    width: 50%;
    padding: 12px 20px;
    cursor: pointer;
}
.button:first-child {
    border-right: 1px solid var(--G100);
}
</style>