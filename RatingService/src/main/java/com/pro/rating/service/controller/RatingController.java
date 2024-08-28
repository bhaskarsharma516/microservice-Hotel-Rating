package com.pro.rating.service.controller;

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

import com.pro.rating.service.entities.Rating;
import com.pro.rating.service.services.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {
	
	@Autowired
	private RatingService ratingService;

	@PostMapping("/addrating")
	public ResponseEntity<Rating>create(@RequestBody Rating rating){
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Rating>> getRatings(){
		return ResponseEntity.ok(ratingService.getAll());
	}	
	
	@GetMapping("/users/{userID}")
	public ResponseEntity<List<Rating>> getRatingsbyUserId(@PathVariable String userID){
		return ResponseEntity.ok(ratingService.getRatingByUserId(userID));
	}
	
	@GetMapping("hotels/{hotelID}")
	public ResponseEntity<List<Rating>> getRatingsbyHotelId(@PathVariable String hotelID){
		return ResponseEntity.ok(ratingService.getRatingByHotelId(hotelID));
	}

}
