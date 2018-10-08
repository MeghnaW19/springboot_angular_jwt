package com.cognizant.moviecruiserserverapplication.controller;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cognizant.moviecruiserserverapplication.domain.Movie;
import com.cognizant.moviecruiserserverapplication.exception.MovieAlredayExistsException;
import com.cognizant.moviecruiserserverapplication.exception.MovieNotFoundException;
import com.cognizant.moviecruiserserverapplication.service.IMovieService;
import io.jsonwebtoken.Jwts;

@CrossOrigin
@RestController
@RequestMapping("/api/movie")
public class MovieController {

	private IMovieService movieService;

	/**
	 * Constructor injection to movieService
	 * 
	 * @param movieService
	 */
	@Autowired
	public MovieController(IMovieService movieService) {
		this.movieService = movieService;
	}

	/**
	 * method to handle save movie request
	 * 
	 * @param movie
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> saveMovie(@RequestBody final Movie movie,HttpServletRequest req, HttpServletResponse resp) {
		ResponseEntity<?> response;
		final String authHeader = req.getHeader("authorization");
		final String token = authHeader.substring(7);
		String userId = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getSubject();
		System.out.println(userId);
		try {
			movie.setUserId(userId);
			movieService.saveMovie(movie);
			response = new ResponseEntity<Movie>(movie, HttpStatus.CREATED);
		} catch (MovieAlredayExistsException e) {
			response = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
		return response;

	}

	/**
	 * method to handle update movie request
	 * 
	 * @param id
	 * @param movie
	 * @return
	 */
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateMovie(@PathVariable final Integer id, @RequestBody final Movie movie) {
		ResponseEntity<?> response;
		try {setSigningKeysetSigningKey
			final Movie fechedMovie = movieService.updateMovie(movie);
			response = new ResponseEntity<Movie>(fechedMovie, HttpStatus.OK);
		} catch (MovieNotFoundException e) {
			response = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
		return response;
	}

	/**
	 * method to handle delete movie request
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable final Integer id) {
		ResponseEntity<?> response;
		try {
			movieService.deleteMovieById(id);

		} catch (MovieNotFoundException e) {
			response = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
		}
		response = new ResponseEntity<String>("Movie Deleted sucessfully..:)", HttpStatus.OK);
		return response;
	}

	/**
	 * method to handle get movie by id request
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> fetchByMovieId(@PathVariable final Integer id) {
		ResponseEntity<?> response;
		try {
			response = new ResponseEntity<Movie>(movieService.getMovieById(id), HttpStatus.OK);
		} catch (MovieNotFoundException e) {
			response = new ResponseEntity<String>("{ \"message\" : \"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
		}
		return response;
	}

	/**
	 * method to handle get all movies request
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<?> fetchMovies(final ServletRequest req, ServletResponse resp) {
		final HttpServletRequest request = (HttpServletRequest)req;
		final String authHeader = request.getHeader("authorization");
		final String token = authHeader.substring(7);
		String userId = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getSubject();
		System.out.println(userId);
		return new ResponseEntity<List<Movie>>(movieService.getMyMovie(userId), HttpStatus.OK);
	}

}
