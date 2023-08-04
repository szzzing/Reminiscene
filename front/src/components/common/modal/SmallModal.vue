<template>
    <div class="modal-background">
        <div class="modal-wrapper">
            <div class="container">
                <div class="modal-header">
                    <slot name="header"></slot>
                </div>
                <div class="modal-footer">
                    <slot name="footer"></slot>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.modal-background {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
    transition: opacity .3s ease;
}
.modal-wrapper {
    display: table-cell;
    vertical-align: middle;
}
.container {
    background: var(--G0);
    max-width: 400px;
    height: 200px;
    border-radius: 16px;
}
</style>