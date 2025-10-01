package com.xpress.user_service.service;

import com.xpress.user_service.domain.User;
import com.xpress.user_service.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> findByEmail(String email);

    Mono<UserDto> createUser(UserDto userDto);
}
