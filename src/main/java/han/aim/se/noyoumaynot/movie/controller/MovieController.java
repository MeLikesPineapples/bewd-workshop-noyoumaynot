package han.aim.se.noyoumaynot.movie.controller;

import han.aim.se.noyoumaynot.movie.domain.Movie;
import han.aim.se.noyoumaynot.movie.domain.User;
import han.aim.se.noyoumaynot.movie.repository.UserToken;
import han.aim.se.noyoumaynot.movie.service.AuthenticationService;
import han.aim.se.noyoumaynot.movie.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.ArrayList;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final AuthenticationService authenticationService;
    public User user;
    public String token;

    @Autowired
    public MovieController(MovieService movieService, AuthenticationService authenticationService) {
        this.movieService = movieService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
<<<<<<< Updated upstream
        token = authenticationService.login(user.getUsername(), user.getPassword()).getToken();
=======
        token = authenticationService.login(user.getUsername(), user.getPassword());
        this.user = user;
>>>>>>> Stashed changes
        return token;
    }

    @GetMapping
    public ArrayList<Movie> getAllMovies(@RequestParam String token, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws Exception {
        authenticate(token, authorization);
        return movieService.getMovieList();
    }

    @GetMapping("/show")
    public Movie getMovieById(@RequestParam("id") String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws Exception {
            authenticate(token, authorization);
            Movie movie = movieService.getMovieById(id);
            return movie;
    }

    @PostMapping("/add")
    public Movie addMovie(@RequestBody Movie movie, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws Exception {
        authenticate(token, authorization);
        if(authenticationService.isAdmin(token, user)) {
            movieService.insertMovie(movie);
            return movie;
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws Exception {
        authenticate(token, authorization);
        if(authenticationService.isAdmin(token, user)) {
            movieService.deleteMovie(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(403).build();
    }

    private String authenticate(String token, String authorization) throws Exception {
        if (authenticationService.isValidToken(token)){
            return authenticationService.getUsername(token);
        } else {
            throw new AuthenticationException("Invalid token");
        }
    }


}
