package com.demo.mainservice.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.demo.mainservice.security.dto.UserDto;

@FeignClient(value = "user-service", url = "${spring.settings.user-service.uri}")
public interface UserClient {

    @GetMapping("/validate")
    UserDto validate(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authHeader); // maybe use header to transfer token?
}
