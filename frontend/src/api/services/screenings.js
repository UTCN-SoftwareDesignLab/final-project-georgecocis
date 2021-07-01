import authHeader, { BASE_URL, HTTP } from "../http";
export default {
  allScreenings() {
    return HTTP.get(BASE_URL + "/screenings", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  createScreening(screening) {
    return HTTP.post(BASE_URL + "/screenings", screening, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },

  updateScreening(screening) {
    return HTTP.patch(BASE_URL + "/screenings/" + screening.id, screening, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  deleteScreening(id) {
    return HTTP.delete(BASE_URL + "/screenings/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
