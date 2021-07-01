import authHeader, { BASE_URL, HTTP } from "../http";
export default {
  allTickets() {
    return HTTP.get(BASE_URL + "/tickets", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  allScreenings() {
    return HTTP.get(BASE_URL + "/tickets/ticketscreening", { headers: authHeader() }).then(
        (response) => {
      return response.data;
        }
    );
  },

  allMovies() {
    return HTTP.get(BASE_URL + "/tickets/ticketmovies", { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },

  exportReport() {
    return HTTP.get(BASE_URL + "/tickets/ticketscreening/PDF", { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },

  getMovie(id) {
    return HTTP.get(BASE_URL + "/tickets/ticketscreening/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  createTicket(ticket) {
    return HTTP.post(BASE_URL + "/tickets", ticket, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  deleteTicket(id) {
    return HTTP.delete(BASE_URL + "/tickets/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
