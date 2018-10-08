package com.cognizant.moviecruiserserverapplication.service;

import java.util.List;

import com.cognizant.moviecruiserserverapplication.domain.Movie;
import com.cognizant.moviecruiserserverapplication.exception.MovieAlredayExistsException;
import com.cognizant.moviecruiserserverapplication.exception.MovieNotFoundException;

public interface IMovieService {

	public boolean saveMovie(final Movie movie) throws MovieAlredayExistsException;

	public Movie updateMovie(final Movie movie) throws MovieNotFoundException;

	public boolean deleteMovieById(final int id) throws MovieNotFoundException;

	public Movie getMovieById(final int id) throws MovieNotFoundException;

	public List<Movie> getAllMovies();
	
	public List<Movie> getMyMovie(String userId);

}
