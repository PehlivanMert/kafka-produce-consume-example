package org.pehlivanmert.kafkaexample.controller;

import lombok.RequiredArgsConstructor;
import org.pehlivanmert.kafkaexample.dto.AddressResponseDto;
import org.pehlivanmert.kafkaexample.dto.UserCreateRequest;
import org.pehlivanmert.kafkaexample.dto.UserResponse;
import org.pehlivanmert.kafkaexample.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private RestTemplate restTemplate = new RestTemplate();
    private final org.pehlivanmert.kafkaexample.service.UserService userService;

    @PostMapping
    public User create(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.create(userCreateRequest);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserAddress(@PathVariable Long userId) {
        // You have to change user-address service port according to running port
        String url = String.format("http://localhost:8002/api/address/%s", userId);
        ResponseEntity<AddressResponseDto> address = restTemplate.getForEntity(url, AddressResponseDto.class);

        User user = userService.getUserById(address.getBody().getUserId());
        return UserResponse.getUserResponseWithAddress(user, address.getBody());

    }
}