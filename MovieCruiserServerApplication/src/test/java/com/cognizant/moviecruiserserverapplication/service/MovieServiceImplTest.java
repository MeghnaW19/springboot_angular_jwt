package com.cognizant.moviecruiserserverapplication.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cognizant.moviecruiserserverapplication.domain.Movie;
import com.cognizant.moviecruiserserverapplication.exception.MovieAlredayExistsException;
import com.cognizant.moviecruiserserverapplication.exception.MovieNotFoundException;
import com.cognizant.moviecruiserserverapplication.repo.MovieRepository;

public class MovieServiceImplTest {

	/**
	 * Mocking MovieRepository object 
	 */
	@Mock
	private transient MovieRepository movieRepository;
	
	/**
	 * Mocking Movie object 
	 */
	private transient Movie movie;
	
	/**
	 * Injecting mock in MovieServiceImpl 
	 */
	@InjectMocks
	private transient MovieServiceImpl movieServiceImpl;
	
	/**
	 * variable to hold user defined movie list
	 */
	transient Optional<Movie> optionals;
	
	@Before 
	public void setUpMock() {
		MockitoAnnotations.initMocks(this);
		movie = new Movie();
		movie.setId(1);
		movie.setTitle("Anitha Kannan");
		movie.setPoster_path("https://www.google.com");
		movie.setComments("nice movie");
		optionals = Optional.of(movie);
	}
	
	/**
	 * Testing mock creation
	 */
	@Test
	public void testMockCreation() {
		assertNotNull("jpa creation fails, use @Inject on movieServiceImpl", movie);
	}
	
	/**
	 * Testing save move functionality
	 * 
	 * @throws MovieAlredayExistsException
	 */
	@Test
	public void testSaveMovieSucess() throws MovieAlredayExistsException {
		Mockito.when(movieRepository.save(movie)).thenReturn(movie);
		final boolean result = movieServiceImpl.saveMovie(movie);
		assertTrue("saving movie failed, saveMove method return false", result);
		verify(movieRepository, Mockito.times(1)).save(movie);
		verify(movieRepository, Mockito.times(1)).findById(movie.getId());
	}
	
	/**
	 * Testing save movie failure
	 *  
	 * @throws MovieAlredayExistsException
	 */
	@Test(expected = MovieAlredayExistsException.class)
	public void testSaveMovieFailure()throws MovieAlredayExistsException {
		Mockito.when(movieRepository.findById(1)).thenReturn(optionals);
		Mockito.when(movieRepository.save(movie)).thenReturn(movie);
		final boolean result = movieServiceImpl.saveMovie(movie);
		assertFalse("saving movie failed, saveMove method return false", result);
		verify(movieRepository, Mockito.times(1)).findById(movie.getId());
	}
	
	/**
	 * Testing update movie functionality 
	 * 
	 * @throws MovieNotFoundException
	 */
	@Test
	public void testUpdateMovie() throws MovieNotFoundException {
		Mockito.when(movieRepository.findById(1)).thenReturn(optionals);
		Mockito.when(movieRepository.save(movie)).thenReturn(movie);
		movie.setComments("good movie");
		final Movie updateMovie = movieServiceImpl.updateMovie(movie);
		assertEquals("movie update fails",  "good movie", updateMovie.getComments());
		verify(movieRepository, Mockito.times(1)).save(movie);
		verify(movieRepository, Mockito.times(1)).findById(movie.getId());
	}
	
	/**
	 * Testing delete move functionality
	 * 
	 * @throws MovieNotFoundException
	 */
	@Test
	public void testDeleteMovieById() throws MovieNotFoundException{
		Mockito.when(movieRepository.findById(1)).thenReturn(optionals);
		doNothing().when(movieRepository).delete(movie);
		final boolean result = movieServiceImpl.deleteMovieById(1);
		assertTrue("movie delet fails", result);
		verify(movieRepository, Mockito.times(1)).delete(movie);
		verify(movieRepository, Mockito.times(1)).findById(movie.getId());
	}
	
	/**
	 * Testing get movie by id functionality
	 * 
	 * @throws MovieNotFoundException
	 */
	@Test
	public void testGetMovieById()throws MovieNotFoundException {
		Mockito.when(movieRepository.findById(1)).thenReturn(optionals);
		final Movie movieById = movieServiceImpl.getMovieById(1);
		assertEquals("", movieById, movie);
		verify(movieRepository, Mockito.times(1)).findById(movie.getId());
	}
	
	/**
	 * testing fetch all movie functionality 
	 */
	@Test
	public void testGetAllMovies() {
		final List<Movie> movieList = new ArrayList<>(1);
		Mockito.when(movieRepository.findAll()).thenReturn(movieList);
		final List<Movie> movies = movieServiceImpl.getAllMovies();
		assertEquals("get all movies fails",  movieList, movies);
		verify(movieRepository, Mockito.times(1)).findAll();
	}
	
}
