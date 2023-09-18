<template>
    <div id="user">
        <title-component>
            <template v-slot:emoji>👼</template>
            <template v-slot:title>유저 관리</template>
        </title-component>
    </div>
</template>

<script>
import TitleComponent from '../item/TitleComponent.vue'
export default {
  components: { TitleComponent },

}
</script>

<style>

</style>