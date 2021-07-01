import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import MovieList from "../views/MovieList";
import ScreeningList from "../views/ScreeningList";
import ViewTicketList from "../views/ViewTicketList";
import CreateTicketsList from "../views/CreateTicketsList";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Patients" });
      }
    },
  },
  {
    path: "/movies",
    name: "Movies",
    component: MovieList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/screenings",
    name: "Screenings",
    component: ScreeningList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/tickets",
    name: "ViewTickets",
    component: ViewTicketList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isEmployee) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/tickets/ticketscreening",
    name: "CreateTickets",
    component: CreateTicketsList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isEmployee) {
        next();
      } else {
        next({ name: "Home" });
      }
    },
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
