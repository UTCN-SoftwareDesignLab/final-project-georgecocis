<template>
  <v-card>
    <v-card-title>
      Employee panel: View Tickets
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="tickets"
      :search="search"
      @click:row="ticketOperations"
    ></v-data-table>
    <v-btn @click="deleteTicket">DELETE SELECTED TICKET</v-btn>
    <v-btn @click="switchToCreateTickets">VIEW SCREENINGs & MOVIES</v-btn>
  </v-card>
</template>

<script>
import router from "../router";
import api from "../api";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "ViewTicketList",
  components: {},
  data() {
    return {
      tickets: [],
      search: "",
      headers: [
        {
          text: "Id",
          align: "start",
          sortable: false,
          value: "id",
        },
        { text: "Movie ID", value: "movie" },
        { text: "Room ID", value: "room" },
        { text: "Holder name", value: "holder" },
        { text: "Ticket Price", value: "price" },
        { text: "Seat Number", value: "seat" },
      ],
      selectedTicket: {},
    };
  },
  methods: {
    ticketOperations(ticket) {
      this.selectedTicket = ticket;
      console.log("Selected a ticket for deletion");
    },
    switchToCreateTickets() {
      router.push("/tickets/ticketscreening");
    },
    async deleteTicket() {
      await api.tickets
          .deleteTicket(this.selectedTicket.id)
      await this.refreshList();
      console.log("Ticket deleted.")
    },
    openSocketConnection(){
      this.socket = new SockJS("http://localhost:8088/gs-guide-websocket");
      this.stomp = new Stomp.over(this.socket);
      this.stomp.connect({}, (frame) => {console.log(frame), this.stomp.subscribe("/tickets", (msg) => {alert(msg.body)})}, (error) => {console.log(error)});
    },
    async refreshList() {
      this.selectedTicket = {};
      this.tickets = await api.tickets.allTickets();
    },
  },
  created: function () {
    this.openSocketConnection();
    this.refreshList();
  },
};
</script>

<style scoped></style>
