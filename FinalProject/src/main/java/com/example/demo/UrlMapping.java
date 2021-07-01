package com.example.demo;

public class UrlMapping {
    /* Api */
    public static final String API_PATH = "/api";

    /* User-related mappings */
    public static final String USERS = API_PATH + "/users";
    public static final String USER_ENTITY = "/{userId}";

    /* Movie-related mappings */
    public static final String MOVIES = API_PATH + "/movies";
    public static final String MOVIE_ENTITY = "/{movieId}";

    /* Screening-related mappings */
    public static final String SCREENINGS = API_PATH + "/screenings";
    public static final String SCREENING_ENTITY = "/{screeningId}";

    /* Ticket-related mappings */
    public static final String TICKETS = API_PATH + "/tickets";
    public static final String TICKET_ENTITY =  "/{ticketId}";
    public static final String TICKET_SCREENINGS = "/ticketscreening";
    public static final String PDF = TICKET_SCREENINGS + "/PDF";
    public static final String TICKET_MOVIES = "/ticketmovies";
    public static final String TICKET_MOVIE_ENTITY = TICKET_SCREENINGS + "/{tmovieId}";

    /* Sign in/up*/
    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
}
