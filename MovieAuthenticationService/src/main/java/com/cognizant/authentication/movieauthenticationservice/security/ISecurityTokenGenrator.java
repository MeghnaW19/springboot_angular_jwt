package com.cognizant.authentication.movieauthenticationservice.security;

import java.util.Map;

import com.cognizant.authentication.movieauthenticationservice.model.User;

public interface ISecurityTokenGenrator {

	public Map<String, String> generateToken(User user);
	
}
