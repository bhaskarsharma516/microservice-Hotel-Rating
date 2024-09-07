package com.pro.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.gateway.services.JwtHelper;

@RestController
@RequestMapping("/config")
public class PublicController {
	

	@GetMapping("/publicKey")
	public String getPublicKey() {
		return JwtHelper.getPublicKey();
	}
}
