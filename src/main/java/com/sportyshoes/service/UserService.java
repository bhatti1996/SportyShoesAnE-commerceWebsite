package com.sportyshoes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sportyshoes.model.User;
import com.sportyshoes.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	public List<User> getAllUser() {
		
		return userRepository.findAll();
	}
	public Optional<User> getUserById(int id) {
		
		return userRepository.findById(id);
	}
	
	public List<User> getByEmailId(String email) {
		  
		Optional<User> user = userRepository.findUserByEmail(email);
		user.orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
		return (List<User>) user.get();
	}
}
