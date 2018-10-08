package com.cognizant.moviecruiserserverapplication.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cognizant.moviecruiserserverapplication.domain.Movie;
import com.cognizant.moviecruiserserverapplication.service.IMovieService;
import com.google.gson.Gson;


@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {

	/**
	 * Reference to mockMVC instance
	 */
	@Autowired
	private transient MockMvc mockMvc;

	/**
	 * Reference to MovieService instance
	 */
	@MockBean
	private transient IMovieService movieService;

	/**
	 * Reference to Movie instance
	 */
	private transient Movie movie;

	/**
	 * Defined movie list
	 */
	static List<Movie> movies;

	/**
	 * Initial setup instances
	 */
	@Before
	public void init() {
		movies = new ArrayList<Movie>();
		movies.add(buildMovie(1, "Manikandan", "Nice person", "https://www.google.com"));
		movies.add(buildMovie(2, "Anitha", "Nice girl", "https://www.google.com"));
		// movie = buildMovie(1, "Manikandan", "Nice person", "https://www.google.com");

	}

	/**
	 * this method build movie object
	 * 
	 * 
	 * @param id
	 * @param name
	 * @param comments
	 * @param posterPath
	 * @return
	 */
	private Movie buildMovie(int id, String name, String comments, String posterPath) {
		movie = new Movie();
		movie.setId(id);
		movie.setTitle(name);
		movie.setComments(comments);
		movie.setPoster_path(posterPath);
		movie.setUserId("Manikandankasi");
		return movie;

	}

	/**
	 * this method convert movie object into json string
	 * 
	 * @param movie
	 * @return
	 */
	private String jsonToString(Movie movie) {
		Gson gson = new Gson();
		String result = gson.toJson(movie);
		System.out.println(result);
		return result;
	}

	/**
	 * method to test save movie functionality
	 * 
	 * @throws Exception
	 */
	// @Ignore
	@Test
	public void testSaveMovieSucess() throws Exception {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNYW5pa2FuZGFua2FzaSIsImlhdCI6MTUzMzQ5MDUwN30.p4wRMhOTGdoJUzCTb2evkRx2ji6dqz7sg5frOFofc8A";
		Mockito.when(movieService.saveMovie(Mockito.any(Movie.class))).thenReturn(true);
		mockMvc.perform(post("/api/movie").header("authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(jsonToString(movie))).andExpect(status().isCreated())
				.andDo(print());
		verify(movieService, times(1)).saveMovie(Mockito.any(Movie.class));
		// verifyNoMoreInteractions(movieService);
	}

	/**
	 * method to test update movie functionality
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateMovieSucess() throws Exception {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNYW5pa2FuZGFua2FzaSIsImlhdCI6MTUzMzQ5MDUwN30.p4wRMhOTGdoJUzCTb2evkRx2ji6dqz7sg5frOFofc8A";
		movie.setTitle("AnithaKannan");
		Mockito.when(movieService.updateMovie(movie)).thenReturn(movies.get(0));
		mockMvc.perform(put("/api/movie/{id}", 1).header("authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(jsonToString(movie))).andExpect(status().isOk())
				.andDo(print());
		verify(movieService, times(1)).updateMovie(Mockito.any(Movie.class));
		verifyNoMoreInteractions(movieService);

	}

	/**
	 * method to test delete movie functionality
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteMovieSucess() throws Exception {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNYW5pa2FuZGFua2FzaSIsImlhdCI6MTUzMzQ5MDUwN30.p4wRMhOTGdoJUzCTb2evkRx2ji6dqz7sg5frOFofc8A";
		Mockito.when(movieService.deleteMovieById(1)).thenReturn(true);
		mockMvc.perform(delete("/api/movie/{id}", 1).header("authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
		verify(movieService, times(1)).deleteMovieById(Mockito.any(Integer.class));
		verifyNoMoreInteractions(movieService);
	}

	/**
	 * method to test get movie by id functionality
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetMovieById() throws Exception {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNYW5pa2FuZGFua2FzaSIsImlhdCI6MTUzMzQ5MDUwN30.p4wRMhOTGdoJUzCTb2evkRx2ji6dqz7sg5frOFofc8A";
		Mockito.when(movieService.getMovieById(1)).thenReturn(movies.get(0));
		mockMvc.perform(get("/api/movie/{id}", 1).header("authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
		verify(movieService, times(1)).getMovieById(Mockito.any(Integer.class));
		verifyNoMoreInteractions(movieService);

	}

	/**
	 * method to test get all movies
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetAllMovies() throws Exception {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNYW5pa2FuZGFua2FzaSIsImlhdCI6MTUzMzQ5MDUwN30.p4wRMhOTGdoJUzCTb2evkRx2ji6dqz7sg5frOFofc8A";
		Mockito.when(movieService.getMyMovie(Mockito.anyString())).thenReturn(movies);
		mockMvc.perform(
				get("/api/movie", 1).header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
		verify(movieService, times(1)).getMyMovie(Mockito.anyString());
		verifyNoMoreInteractions(movieService);
	}
}
