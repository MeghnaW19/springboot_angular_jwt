package com.cognizant.moviecruiserserverapplication.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.moviecruiserserverapplication.domain.Movie;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class MovieRepositoryTest {

	/**
	 * Reference to MovieRepository
	 */
	@Autowired
	private transient MovieRepository movieRepository;

	private Movie movie;

	public void setMovieRepository(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	/**
	 * Initialize movie instance
	 */
	@Before
	public void setUp() {
		movie = new Movie();
		movie.setId(1);
		movie.setTitle("MovieName");
		movie.setPoster_path("https://www.google.com");
		movie.setComments("I am done spring boot application");
		movieRepository.save(movie);
	}

	@After
	public void tearDown() {
		movie = null;
	}

	/**
	 * method to test save move functionality
	 */
	@Test
	public void testSaveMovie() {
		movie = null;
		movie = movieRepository.findById(1).orElse(null);
		assertNotNull(movie);
		assertEquals("MovieName", movie.getTitle());
		assertEquals("I am done spring boot application", movie.getComments());
	}

	/**
	 * method to test update movie functionality
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateMovie() throws Exception {
		movie = null;
		movie = movieRepository.findById(1).orElse(null);
		movie.setTitle("Anitha Kannan");
		movieRepository.save(movie);
		movie = null;
		movie = movieRepository.findById(1).orElse(null);
		assertNotNull(movie);
		assertEquals("Anitha Kannan", movie.getTitle());
	}

	/**
	 * method to test delete movie functionality
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteMovie() throws Exception {
		movieRepository.deleteById(1);
		assertNull(movieRepository.findById(1).orElse(null));
	}

	/**
	 * method to test get movie functionality
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetMovie() throws Exception {
		movie = null;
		movie = movieRepository.findById(1).orElse(null);
		assertNotNull(movie);
		assertEquals("MovieName", movie.getTitle());
		assertEquals("I am done spring boot application", movie.getComments());
	}

	/**
	 * method to test get all movie functionality
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetAllMovies() throws Exception {
		assertTrue(movieRepository.findAll().size()>0);
	}

}
