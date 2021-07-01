<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create item" : "Edit item" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="movie.title" label="Title" />
          <v-text-field v-model="movie.description" label="Description" />
          <v-text-field v-model="movie.duration" label="Duration" />
          <v-text-field v-model="movie.ticketPrice" label="Ticket price" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deleteMovie">Delete Movie</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "MovieDialog",
  props: {
    movie: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.movies
          .createMovie({
            title: this.movie.title,
            description: this.movie.description,
            duration: this.movie.duration,
            ticketPrice: this.movie.ticketPrice,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.movies
          .updateMovie({
            id: this.movie.id,
            title: this.movie.title,
            description: this.movie.description,
            duration: this.movie.duration,
            ticketPrice: this.movie.ticketPrice,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteMovie() {
      api.movies.deleteMovie(this.movie.id).then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.movie.id;
    },
  },
};
</script>

<style scoped></style>
