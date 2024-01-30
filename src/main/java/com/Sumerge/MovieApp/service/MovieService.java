package com.Sumerge.MovieApp.service;

import com.Sumerge.MovieApp.entity.User;
import com.Sumerge.MovieApp.model.Movie;
import com.Sumerge.MovieApp.repository.MovieRepo;
import com.Sumerge.MovieApp.repository.UserRepo;
import io.micrometer.observation.ObservationFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public class MovieService {

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private ModelMapper mapper;

    public Movie getUserById(long id) {
        Optional<Movie> movie = movieRepo.findById(id);
        Movie movieResponse = mapper.map(movie, Movie.class);
        return movieResponse;
    }
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieRepo.findAll();
        return movies.stream()
                .map(movie -> mapper.map(movie, Movie.class))
                .collect(Collectors.toList());
    }
}
