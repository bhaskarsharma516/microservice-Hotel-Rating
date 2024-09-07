package com.config.server.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.config.server.extServices.GatewayServices;
import com.config.server.services.ConfigService;

@Component
public class ConfigServiceImpl implements ConfigService {
	
	@Value("${config.repo.path}")
	private String LOCAL_REPO_PATH;
	
	@Value("${config.repo.username}")
	private String USERNAME;
	
	@Value("${config.repo.password}")
	private String PASSWORD;
	
	@Autowired
	private GatewayServices gateway;

	@Override
	public String updateConfiguration() throws IOException, NoFilepatternException, GitAPIException, InvalidKeySpecException, NoSuchAlgorithmException {
		
		  
		 String base64PublicKey=gateway.getPublicKey();
		 
		 // Decode the Base64-encoded public key into PublicKey for verification as 
		 //JWT sign with PrivateKey and then PublicKey encoded to string into base64 to make it easier to serialize
		 //convert it back to its original 
//	        byte[] decodedKey = Base64.getDecoder().decode(base64PublicKey);
//	        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
//	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//	        PublicKey publicKey=keyFactory.generatePublic(keySpec);
	        
	     File localRepoDir = new File(LOCAL_REPO_PATH);

    	 if (!localRepoDir.exists() && !localRepoDir.isDirectory()) {
    		 Git git = Git.cloneRepository()
                 .setURI("https://github.com/bhaskarsharma516/microservice-hotel-rating-config.git")
                 .setBranch("main")
                 .setDirectory(localRepoDir)
                 .setCredentialsProvider(new UsernamePasswordCredentialsProvider(USERNAME, PASSWORD))
                 .call();
    }
		
		    try (Git git = Git.open(localRepoDir)) {
		        // Update the configuration file
		        File configFile = new File(localRepoDir, "application.properties");
		        String newContent = "public.key" + "=" + base64PublicKey + "\n";
		         Files.write(Paths.get(configFile.getPath()), newContent.getBytes(), java.nio.file.StandardOpenOption.APPEND);

		        // Commit and push changes
		        git.add().addFilepattern("application.properties").call();
		        git.commit().setMessage("Update configuration").call();
		        git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(USERNAME, PASSWORD)).call();
		    }

		    return "Configuration updated successfully!";
	}
	}


