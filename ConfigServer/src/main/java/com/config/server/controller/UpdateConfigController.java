package com.config.server.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.config.server.services.ConfigService;

@RestController
@RequestMapping("/config")
public class UpdateConfigController {
	
	@Autowired
	private ConfigService service;

	@PostMapping("/update")
	public ResponseEntity<String> updateConfig() throws NoFilepatternException, IOException, GitAPIException, InvalidKeySpecException, NoSuchAlgorithmException {
		String res=service.updateConfiguration();
		return new ResponseEntity<>(res,HttpStatus.OK);
   
	}
	
	


}