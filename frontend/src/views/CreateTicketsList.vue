<template>
  <v-card>
    <v-card-title>
      Employee panel: Create Tickets
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
      :items="screenings"
      :search="search"
      @click:row="selectScreening"
    ></v-data-table>
    <TicketDialog
      :opened="dialogVisible"
      :screening="selectedScreening"
      :movie="selectedMovie"
      @refresh="refreshList"
    ></TicketDialog>
    <v-data-table
        :headers="header1"
        :items="movies"
        :search="search"
        @click:row="selectMovie"
    ></v-data-table>
    <v-btn @click="switchToExistingTickets">EXISTING TICKETS</v-btn>
    <v-btn @click="openDialog">Create Ticket</v-btn>
    <v-btn @click="createReport">Income report</v-btn>

  </v-card>
</template>

<script>
import TicketDialog from "../components/TicketDialog";
import router from "../router";
import api from "../api";

export default {
  name: "CreateTicketsList",
  components: { TicketDialog },
  data() {
    return {
      screenings: [],
      movies: [],
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
        { text: "Time of screening", value: "timeOfScreening" },
        { text: "Duration", value: "screeningDuration" },
      ],
      header1: [
        {
          text: "Id",
          align: "start",
          sortable: false,
          value: "id",
        },
        { text: "Title", value: "title" },
        { text: "Description", value: "description" },
        { text: "Duration", value: "duration" },
        { text: "Ticket price", value: "ticketPrice" },
        { text: "Income", value: "income"}
      ],
      dialogVisible: false,
      selectedScreening: {},
      selectedMovie: {},
    };
  },
  methods: {
    selectScreening(screening) {
      this.selectedScreening = screening;
      console.log("Selected a screening.");
    },
    selectMovie(movie){
      this.selectedMovie = movie;
      console.log("Selected a movie");
    },
    openDialog(){
      this.dialogVisible = true;
    },
    createReport(){
      api.tickets.exportReport();
      console.log("Report created. Check under main/java/resources");
    },
    switchToExistingTickets() {
      router.push("/tickets");
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedScreening = {};
      this.screenings = await api.tickets.allScreenings();
      this.movies = await api.tickets.allMovies();
    },
  },
  created: function () {
    this.refreshList();
    console.log("In order to create a ticket:");
    console.log("1.Select a screening");
    console.log("2.Select the proper movie (movie.id = screening.movie.id");
  },
};
</script>

<style scoped></style>
