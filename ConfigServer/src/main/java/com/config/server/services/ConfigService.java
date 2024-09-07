package com.config.server.services;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.springframework.stereotype.Service;

@Service
public interface ConfigService {

	public String updateConfiguration() throws IOException, NoFilepatternException, GitAPIException,InvalidKeySpecException,NoSuchAlgorithmException;
}
