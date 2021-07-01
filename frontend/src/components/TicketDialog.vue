<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ "Create Ticket" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="holder" label="Ticket Holder" />
          <v-text-field v-model="seat" label="Ticket seat" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ "Create" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "TicketDialog",
  props: {
    holder: String,
    seat: String,
    movie: Object,
    screening: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      api.tickets
        .createTicket({
          holder: this.holder,
          price: this.movie.ticketPrice,
          seat: this.seat,
          movie: this.movie.title,
          room: this.screening.room,
        })
        .then(() => this.$emit("refresh"))
        .then(() => console.log("Ticked created"));
    },
  },
  computed: {},
};
</script>

<style scoped></style>
