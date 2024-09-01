package com.pro.gateway.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.pro.gateway.models.User;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepo extends ReactiveCrudRepository<User, String> {

	Mono<User> findByUsername(String username);
}

