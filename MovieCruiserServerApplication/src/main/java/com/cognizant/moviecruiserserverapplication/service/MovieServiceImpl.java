package com.cognizant.moviecruiserserverapplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.moviecruiserserverapplication.domain.Movie;
import com.cognizant.moviecruiserserverapplication.exception.MovieAlredayExistsException;
import com.cognizant.moviecruiserserverapplication.exception.MovieNotFoundException;
import com.cognizant.moviecruiserserverapplication.repo.MovieRepository;

@Service
public class MovieServiceImpl implements IMovieService {

	private final transient MovieRepository movieRepository;

	/**
	 * Constructor injection for movieRepository
	 * 
	 * @param movieRepository
	 */
	@Autowired
	public MovieServiceImpl(final MovieRepository movieRepository) {
		super();
		this.movieRepository = movieRepository;
	}

	/**
	 * method to save movie
	 */
	@Override
	public boolean saveMovie(final Movie movie) throws MovieAlredayExistsException {
		// TODO Auto-generated method stub
		final Optional<Movie> optional = movieRepository.findById(movie.getId());
		if (optional.isPresent()) {
			throw new MovieAlredayExistsException("Could not save Movie, Movie Already Exists");
		}
		movieRepository.save(movie);
		return true;
	}

	/**
	 * method to update movie
	 */
	@Override
	public Movie updateMovie(final Movie updateMovie) throws MovieNotFoundException {
		// TODO Auto-generated method stub
		final Movie movie = movieRepository.findById(updateMovie.getId()).orElse(null);
		if (null == movie) {
			throw new MovieNotFoundException("Could not update Movie, Movie not exists");
		}
		movie.setComments(updateMovie.getComments());
		movieRepository.save(movie);
		return movie;
	}

	/**
	 * method to delete movie by id
	 */
	@Override
	public boolean deleteMovieById(final int id) throws MovieNotFoundException {
		// TODO Auto-generated method stub
		final Movie movie = movieRepository.findById(id).orElse(null);
		if (null == movie) {
			throw new MovieNotFoundException("Could not delete Movie, Movie not exists");
		}
		movieRepository.delete(movie);
		return true;
	}

	/**
	 * method to get movie by id
	 */
	@Override
	public Movie getMovieById(final int id) throws MovieNotFoundException {
		// TODO Auto-generated method stub
		final Movie movie = movieRepository.findById(id).orElse(null);
		if (null == movie) {
			throw new MovieNotFoundException("Could not get Movie, Movie not exists");
		}
		return movie;
	}

	/**
	 * method to get all movies
	 */
	@Override
	public List<Movie> getAllMovies() {
		// TODO Auto-generated method stub
		return movieRepository.findAll();
	}

	@Override
	public List<Movie> getMyMovie(String userId) {
		// TODO Auto-generated method stub
		return movieRepository.findByUserId(userId);
	}

}
