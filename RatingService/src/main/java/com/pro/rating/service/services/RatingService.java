package com.pro.rating.service.services;

import java.util.List;

import com.pro.rating.service.entities.Rating;

public interface RatingService {

	Rating create(Rating rating);
	List<Rating> getAll();
	List<Rating> getRatingByUserId(String userId);
	List<Rating> getRatingByHotelId(String hotelId);
	
}
