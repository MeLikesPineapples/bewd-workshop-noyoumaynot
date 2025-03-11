package han.aim.se.noyoumaynot.movie.controller;

import han.aim.se.noyoumaynot.movie.domain.Movie;
import han.aim.se.noyoumaynot.movie.domain.Role;
import han.aim.se.noyoumaynot.movie.domain.User;
import han.aim.se.noyoumaynot.movie.service.AuthenticationService;
import han.aim.se.noyoumaynot.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class MovieController {
    private final MovieService movieService;
    private final AuthenticationService authenticationService;

    @Autowired
    public MovieController(MovieService movieService, AuthenticationService authenticationService) {
        this.movieService = movieService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return authenticationService.login(user.getUsername(), user.getPassword()).getToken();
    }

    @GetMapping("/movies")
    public List<Movie> getAllMovies(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        authenticate(token);
        return movieService.getMovieList();
    }

    @GetMapping("/movies/show")
    public Movie getMovieById(@RequestParam("id") String id, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        authenticate(token);
        Movie movie = movieService.getMovieById(id);
        return movie;
    }

    @PostMapping("/movies/add")
    public Movie addMovie(@RequestBody Movie movie, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        Role role = authenticate(token);
        if (!role.getIsAdmin()) {
            throw new AuthorizationException("Unauthorized");
        }
        movieService.insertMovie(movie);
        return movie;
    }

    @DeleteMapping("/movies/delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") String id, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        Role role = authenticate(token);
        if (!role.getIsAdmin()) {
            throw new AuthorizationException("Unauthorized");
        }
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }

    private Role authenticate(String token) throws AuthenticationException {
        token = token.replaceAll("^Bearer ", "");
        if (authenticationService.isValidToken(token)) {
            return authenticationService.getRole(token);
        } else {
            throw new AuthenticationException("Invalid token");
        }
    }
}
