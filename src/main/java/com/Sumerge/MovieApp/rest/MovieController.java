package com.Sumerge.MovieApp.rest;

import com.Sumerge.MovieApp.entity.User;
import com.Sumerge.MovieApp.model.Movie;
import com.Sumerge.MovieApp.service.MovieService;
import com.Sumerge.MovieApp.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
    @RestController
    @CrossOrigin(origins = {"http://localhost:4200"})
    @RequestMapping("/api/v1/users")
    public class MovieController {

        private final MovieService movieService;
        private final UserService userService;

//        @GetMapping("/{userId}/watchlist")
//        public List<MovieDto> getWatchlistMovies(@PathVariable Long userId) {
//            return movieService.getWatchlistMovies(userId);
//        }
//
//        @PostMapping("/{userId}/watchlist")
//        @ResponseStatus(HttpStatus.CREATED)
//        public void addMovieToWatchlist(@PathVariable Long userId, @RequestBody MovieDto movieDto) {
//            movieService.addToWatchlist(userId, movieDto);
//        }
//
//        @DeleteMapping("/{userId}/watchlist/{movieId}")
//        @ResponseStatus(HttpStatus.NO_CONTENT)
//        public void removeMovieFromWatchlist(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId) {
//            movieService.removeFromWatchlist(userId, movieId);
//        }

        @GetMapping("/{userId}")
        public ResponseEntity<User> getUserData(@PathVariable("userId") Long userId) {
            User user = userService.getUserById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

//    @GetMapping("/movies/")
//    public ResponseEntity<List<User>> getAllMovies() {
//        List<Movie> movies = userService.getAllMovies();
//        return ResponseEntity.status(HttpStatus.OK).body(movies);
//    }

        @ExceptionHandler({EntityNotFoundException.class})
        public ResponseEntity<String> handleNotFoundException(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

