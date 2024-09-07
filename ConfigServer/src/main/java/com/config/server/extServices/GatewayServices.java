package com.config.server.extServices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="APIGATEWAY")
public interface GatewayServices {

@GetMapping("config/publicKey")
	String getPublicKey();
}
