package com.xpress.user_service.service;

import com.xpress.user_service.domain.User;
import com.xpress.user_service.dto.UserDto;
import com.xpress.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> findByEmail(String email) {
        Mono<User> byEmail = userRepository.findByEmail(email);
        return userRepository.findByEmail(email);
    }

    @Override
    public Mono<UserDto> createUser(UserDto userDto) {

        var user2 = userDto;
        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setPasswordHash(userDto.getPasswordHash());
        newUser.setFirstName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        newUser.setUserType(userDto.getUserType());
        newUser.setActivated(userDto.isActivated());
        userRepository.save(newUser).subscribe();


        return Mono.just(userDto);
    }

}
