package com.pro.user.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pro.user.service.entities.Hotel;
import com.pro.user.service.entities.Rating;
import com.pro.user.service.entities.User;
import com.pro.user.service.exceptions.ResourceNotFoundException;
import com.pro.user.service.exceptions.UsernameAlreadyExistException;
import com.pro.user.service.external.services.HotelService;
import com.pro.user.service.repo.UserRepo;
import com.pro.user.service.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;

	@Override
	public User saveUser(User user) {
		var valid=userRepo.findByUsername(user.getUsername());
		if(valid!=null) {
			throw new UsernameAlreadyExistException("Username already exist.Try with different username");
		}
		String randomUserID=UUID.randomUUID().toString();
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(16);
		user.setPassword(encoder.encode(user.getPassword()));
		user.setUserId(randomUserID);
		return userRepo.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepo.findAll();
	}

	@Override
	public User getUser(String userId) {
		User user= userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with given id is not found on server"+userId));
		
		//calling RatingService API with restTemplate
		Rating[] ratingOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+user.getUserId(), Rating[].class);
		log.info("restTemplate Rating "+ratingOfUser);
		
		List<Rating> ratings=Arrays.stream(ratingOfUser).toList();
		List<Rating> ratingList=ratings.stream().map(rating->{
			
			//calling HotelService API with Feign client interface
			Hotel hotel=hotelService.getHotel(rating.getHotelId());
			
			//calling HotelService API with RestTemplate
		/*	ResponseEntity<Hotel> forEntity=restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
		 * 
		 * 
			log.info("status"+forEntity.getStatusCode());
			Hotel hotel=forEntity.getBody();
		*/
			rating.setHotel(hotel);
			return rating;
			
		}).collect(Collectors.toList());
		
		user.setRatings(ratingList);
		return user;
		
	}


}
