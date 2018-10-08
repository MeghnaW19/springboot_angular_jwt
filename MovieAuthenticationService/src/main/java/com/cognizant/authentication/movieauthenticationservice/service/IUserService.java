package com.cognizant.authentication.movieauthenticationservice.service;

import com.cognizant.authentication.movieauthenticationservice.exception.UserAlreadyExistException;
import com.cognizant.authentication.movieauthenticationservice.exception.UserNotFoundException;
import com.cognizant.authentication.movieauthenticationservice.model.User;

public interface IUserService {

	public boolean saveUser(User user)throws UserAlreadyExistException;
	public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException;
}
