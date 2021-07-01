<template>
  <v-card>
    <v-card-title>
      Admin panel: Movies
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
      :items="movies"
      :search="search"
      @click:row="movieOperations"
    ></v-data-table>
    <v-btn @click="addMovie">Add Movie</v-btn>
    <v-btn @click="switchToUsers">USERS</v-btn>
    <v-btn @click="switchToScreenings">SCREENINGS</v-btn>
    <MovieDialog
      :opened="dialogVisible"
      :movie="selectedMovie"
      @refresh="refreshList"
    ></MovieDialog>
  </v-card>
</template>

<script>
import MovieDialog from "../components/MovieDialog";
import router from "../router";
import api from "../api";
export default {
  name: "MovieList",
  components: { MovieDialog },
  data() {
    return {
      movies: [],
      search: "",
      headers: [
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
      selectedMovie: {},
    };
  },
  methods: {
    addMovie() {
      this.dialogVisible = true;
    },
    movieOperations(movie) {
      this.selectedMovie = movie;
      this.dialogVisible = true;
    },
    switchToUsers() {
      router.push("/users");
    },
    switchToScreenings() {
      router.push("/screenings");
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedMovie = {};
      this.movies = await api.movies.allMovies();
    },
  },
  created: function () {
    this.refreshList();
  },
};
</script>

<style scoped></style>
