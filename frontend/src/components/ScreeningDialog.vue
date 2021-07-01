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
          <v-text-field v-model="screening.movie" label="Movie ID" />
          <v-text-field v-model="screening.room" label="Room ID" />
          <v-text-field
            v-model="screening.timeOfScreening"
            label="Time of screening"
          />
          <v-text-field
            v-model="screening.screeningDuration"
            label="Screening duration"
          />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="deleteScreening">Delete Screening</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ScreeningDialog",
  props: {
    screening: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.screenings
          .createScreening({
            movie: this.screening.movie,
            room: this.screening.room,
            timeOfScreening: this.screening.timeOfScreening,
            screeningDuration: this.screening.screeningDuration,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.screenings
          .updateScreening({
            id: this.screening.id,
            movie: this.screening.movie,
            room: this.screening.room,
            timeOfScreening: this.screening.timeOfScreening,
            screeningDuration: this.screening.screeningDuration,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteScreening() {
      api.screenings
        .deleteScreening(this.screening.id)
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.screening.id;
    },
  },
};
</script>

<style scoped></style>
