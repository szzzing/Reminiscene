<template>
    <div class="container">

        <title-component>
            <template v-slot:emoji>💝</template>
            <template v-slot:title>이번달<br>가장 사랑받은 영화</template>
        </title-component>
        <movie-list-component v-bind:list="list"></movie-list-component>

    </div>
</template>
 
<script>
import MovieListComponent from '../item/MovieListComponent.vue';
import TitleComponent from '../item/TitleComponent.vue';

export default {
    components: {
        TitleComponent,
        MovieListComponent,
    },
    data() {
        return {
            list: [],
        }
    }
}
</script>

<style scoped>
.container {
    max-width: 1280px;
}
</style>