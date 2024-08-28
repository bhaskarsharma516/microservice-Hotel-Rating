package com.pro.rating.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.rating.service.entities.Rating;
import com.pro.rating.service.repo.RatingRepo;
import com.pro.rating.service.services.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepo ratingRepo;
	
	@Override
	public Rating create(Rating rating) {
		rating.setRatingId(UUID.randomUUID().toString());
		return ratingRepo.save(rating);
	}

	@Override
	public List<Rating> getRatingByUserId(String userId) {
		return ratingRepo.findByUserId(userId);
	}

	@Override
	public List<Rating> getRatingByHotelId(String hotelId) {
		return ratingRepo.findByHotelId(hotelId);
	}

	@Override
	public List<Rating> getAll() {
		return ratingRepo.findAll();
	}

}
