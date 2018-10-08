package com.cognizant.authentication.movieauthenticationservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.authentication.movieauthenticationservice.exception.UserAlreadyExistException;
import com.cognizant.authentication.movieauthenticationservice.exception.UserNotFoundException;
import com.cognizant.authentication.movieauthenticationservice.model.User;
import com.cognizant.authentication.movieauthenticationservice.repo.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public boolean saveUser(User user) throws UserAlreadyExistException {

		Optional<User> userResult = userRepository.findById(user.getUserId());

		if (userResult.isPresent()) {
			throw new UserAlreadyExistException("User Already Exist");
		}

		userRepository.save(user);
		return true;
	}

	@Override
	public User findByUserIdAndPassword(String userId, String password)throws UserNotFoundException{
		User authUser = userRepository.findByUserIdAndPassword(userId, password);
		if(authUser == null) {
			throw new UserNotFoundException("Invalid User Id and Password");
		}
		return authUser;
	}

}
