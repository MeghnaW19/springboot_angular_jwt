package com.cognizant.authentication.movieauthenticationservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.authentication.movieauthenticationservice.exception.UserAlreadyExistException;
import com.cognizant.authentication.movieauthenticationservice.exception.UserNotFoundException;
import com.cognizant.authentication.movieauthenticationservice.model.User;
import com.cognizant.authentication.movieauthenticationservice.repo.UserRepository;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {


	@Mock
	private UserRepository repository;
	
	private User user;
	
	@InjectMocks
	private UserServiceImpl userService; 
	
	Optional<User> mockResult;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		user = new User("mainkandankasi", "P@ssw0rd", "Manikandan", "Kasi", new Date(10,10,2018)); 
		mockResult = Optional.of(user);
	}
	
	@After
	public void tearDown() {
		user = null;
		mockResult = null;
	}  
	
	@Test
	public void testRegisterUser() throws Exception{
		when(repository.save(Mockito.any(User.class))).thenReturn(user);
		boolean flag =userService.saveUser(user);
		assertTrue("unable to register user", flag);
		verify(repository, times(1)).save(user);
	}
	
	@Test(expected=UserAlreadyExistException.class)
	public void testRegisterFailure() throws Exception{
		when(repository.findById(user.getUserId())).thenReturn(mockResult);
		when(repository.save(Mockito.any(User.class))).thenReturn(user);
		userService.saveUser(user);
	}

	@Test 
	public void testValidationSucces() throws Exception{
		when(repository.findByUserIdAndPassword(user.getUserId(), user.getPassword())).thenReturn(user);
		User result = repository.findByUserIdAndPassword(user.getUserId(), user.getPassword());
		assertNotNull(result);
		assertEquals(user.getUserId(), result.getUserId());
		verify(repository, times(1)).findByUserIdAndPassword(user.getUserId(), user.getPassword());
	}
	
	@Test(expected=UserNotFoundException.class)
	public void testValidationFailure() throws Exception{
		when(repository.findById("Anitha")).thenReturn(null);
		userService.findByUserIdAndPassword(user.getUserId(), user.getPassword());
	}
}
