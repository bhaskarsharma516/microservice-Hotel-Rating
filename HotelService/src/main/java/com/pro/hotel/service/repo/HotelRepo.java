package com.pro.hotel.service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.hotel.service.entities.Hotel;

public interface HotelRepo extends JpaRepository<Hotel, String> {

}
