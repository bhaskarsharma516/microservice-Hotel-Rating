package com.pro.user.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.user.service.entities.User;
import com.pro.user.service.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/add")
	public ResponseEntity<User> createUser(@RequestBody User user){
		var userData=userService.saveUser(user);
		return  ResponseEntity.status(HttpStatus.CREATED).body(userData);
	}


	@GetMapping("/{userId}")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		var userData=userService.getUser(userId);
		return  ResponseEntity.ok(userData);

	}

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllser(){
		var userData=userService.getAllUser();
		return  ResponseEntity.ok(userData);

	}
}
