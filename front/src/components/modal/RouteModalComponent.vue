<template>
    <transition name="smooth">
        <div id="send-email-modal" class="modal-mask">
            <div class="modal-wrapper">
                <div class="modal-container item-fill">
                    <div class="inner">
                        <div class="emoji shadow"><slot name="emoji"></slot></div>
                        <div class="text"><slot name="text"></slot></div>
                    </div>
                    <div class="inner">
                        <router-link class="medium-button" :to="`${this.route}`"><slot name="route"></slot></router-link>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>

<script>
export default {
    props: [
        'route',
    ],
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
    border-radius: 8px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    padding: 16px 24px;
    gap: 16px;
    text-align: center;
}
.inner {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    flex-direction: column;
}
.emoji {
    font-size: 36px;
}
.medium-button {
    width: 140px;
}
</style>