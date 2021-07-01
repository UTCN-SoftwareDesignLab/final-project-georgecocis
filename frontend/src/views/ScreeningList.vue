<template>
  <v-card>
    <v-card-title>
      Admin panel: Screenings
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
        @click:row="screeningOperations"
    ></v-data-table>
    <v-btn @click="addScreening">Add Screening</v-btn>
    <v-btn @click="switchToUsers">USERS</v-btn>
    <v-btn @click="switchToMovies">MOVIES</v-btn>
    <ScreeningDialog
        :opened="dialogVisible"
        :screening="selectedScreening"
        @refresh="refreshList"
    ></ScreeningDialog>
  </v-card>
</template>

<script>
import ScreeningDialog from "../components/ScreeningDialog";
import router from "../router";
import api from "../api";

export default {
  name: "ScreeningList",
  components: { ScreeningDialog },
  data() {
    return {
      screenings: [],
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
      dialogVisible: false,
      selectedScreening: {},
    };
  },
  methods: {
    addScreening() {
      this.dialogVisible = true;
    },
    screeningOperations(screening) {
      this.selectedScreening = screening;
      this.dialogVisible = true;
    },
    switchToUsers() {
      router.push("/users");
    },
    switchToMovies() {
      router.push("/movies");
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedScreening = {};
      this.screenings = await api.screenings.allScreenings();
    },
  },
  created: function () {
    this.refreshList();
  },
};
</script>

<style scoped></style>
