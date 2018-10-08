package com.cognizant.authentication.movieauthenticationservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.authentication.movieauthenticationservice.model.User;

public interface UserRepository extends JpaRepository<User, String>{

	public User findByUserIdAndPassword(String userId, String password);
	
}
