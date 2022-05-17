package com.sportyshoes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sportyshoes.model.Role;
import com.sportyshoes.model.User;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	//Optional<User> findById(int id);
}
