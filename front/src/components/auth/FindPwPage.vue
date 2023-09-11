<template>
    <find-pw-component v-if="this.emailSuccess==false" @succeedEmail="enableModify"></find-pw-component>
    <modfiy-pw-component v-if="this.emailSuccess==true" v-bind:id="id"></modfiy-pw-component>

</template>

<script>
import FindPwComponent from './FindPwComponent.vue';
import ModfiyPwComponent from './ModfiyPwComponent.vue'

export default {
    components: {
        FindPwComponent,
        ModfiyPwComponent,
    },
    data() {
        return {
            id: null,
            emailSuccess: false,
        }
    },
    methods: {
        enableModify(id) {
            this.id = id;
            this.emailSuccess = true;
        }
    }
}
</script>

<style scoped>
.sub-text, .sub-text * {
    color: var(--G500);
}
.input-area {
    margin: 24px 0 0;
}
.input-box {
    line-height: 48px;
    border-radius: 8px;;
    padding: 12px 24px;
    height: unset;
}
input {
    font-size: 18px;
}
.medium-button {
    width: 90px;
    margin: 20px 0 0 auto;
}
.accent {
    font-weight: 600;
    color: var(--FOCUS);
}
</style>