
package com.cognizant.authentication.movieauthenticationservice.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cognizant.authentication.movieauthenticationservice.model.User;
import com.cognizant.authentication.movieauthenticationservice.security.ISecurityTokenGenrator;
import com.cognizant.authentication.movieauthenticationservice.service.IUserService;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IUserService userService;

	@MockBean
	private ISecurityTokenGenrator securityTokenGenrator;

	private User user;

	@InjectMocks
	private UserController userController;


	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		user = new User("mainkandankasi", "P@ssw0rd", "Manikandan", "Kasi", null);
		
	}

	@After
	public void tearDown() {
		user = null;
	}

	/**
	 * this method convert movie object into json string
	 * 
	 * @param movie
	 * @return
	 */
	private String jsonToString(User user) {
		Gson gson = new Gson();
		String result = gson.toJson(user);
		System.out.println(result);
		return result;
	}

	@Test
	public void testRegisterUser() throws Exception {
		when(userService.saveUser(user)).thenReturn(true);
		mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(user))).andExpect(status().isCreated());
		verify(userService, times(1)).saveUser(Mockito.any(User.class));
		verifyNoMoreInteractions(userService);
	}
 
	@Test
	public void testLoginUser()throws Exception {
		when(userService.findByUserIdAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(user);
		mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(user))).andExpect(status().isOk());
		verify(userService, times(1)).findByUserIdAndPassword(user.getUserId(), user.getPassword());
		verifyNoMoreInteractions(userService);
		
	} 
}
