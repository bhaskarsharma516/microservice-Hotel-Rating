package com.pro.user.service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.user.service.entities.User;

public interface UserRepo extends JpaRepository<User, String> {

	User findByUsername(String username);
}
