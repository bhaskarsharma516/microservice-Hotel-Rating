package com.pro.hotel.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.hotel.service.entities.Hotel;
import com.pro.hotel.service.exceptions.ResourceNotFoundException;
import com.pro.hotel.service.repo.HotelRepo;
import com.pro.hotel.service.services.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	private HotelRepo hotelRepo;
	
	@Override
	public Hotel create(Hotel hotel) {
		hotel.setId(UUID.randomUUID().toString());
		return hotelRepo.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		return hotelRepo.findAll();
	}

	@Override
	public Hotel get(String id) {
//		return new Hotel();
		return hotelRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Hotel with given id not found"));
	}

}
