package com.cognizant.moviecruiserserverapplication.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.moviecruiserserverapplication.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

	public List<Movie> findByUserId(String userId);
}
