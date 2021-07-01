import authHeader, { BASE_URL, HTTP } from "../http";
export default {
  allMovies() {
    return HTTP.get(BASE_URL + "/movies", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  createMovie(movie) {
    return HTTP.post(BASE_URL + "/movies", movie, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  updateMovie(movie) {
    return HTTP.patch(BASE_URL + "/movies/" + movie.id, movie, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  deleteMovie(id) {
    return HTTP.delete(BASE_URL + "/movies/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
