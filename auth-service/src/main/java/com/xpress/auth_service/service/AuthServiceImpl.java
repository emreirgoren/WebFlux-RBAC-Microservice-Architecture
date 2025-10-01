package com.xpress.auth_service.service;

import com.xpress.auth_service.client.UserClient;
import com.xpress.auth_service.dto.LoginVM;
import com.xpress.auth_service.dto.RegisterVM;
import com.xpress.auth_service.dto.UserDto;
import com.xpress.auth_service.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserClient userClient, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userClient = userClient;
        this.passwordEncoder = new BCryptPasswordEncoder();;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<String> login(LoginVM loginVM) {

        Mono<UserDto> byEmail = userClient.findByEmail(loginVM.getEmail());

        return userClient.findByEmail(loginVM.getEmail())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")))
                .flatMap(user -> {
                    if (!passwordEncoder.matches(loginVM.getPassword(), user.getPasswordHash())) {
                        return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
                    }

                    String token = jwtUtil.generateToken(user);
                    return Mono.just(token);
                });
    }

    @Override
    public Mono<String> register(RegisterVM registerVM) {

        return userClient.findByEmail(registerVM.getEmail())
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use"));
                    } else {
                        String hashedPassword = passwordEncoder.encode(registerVM.getPassword());
                        UserDto newUser = createUserDto(registerVM, hashedPassword);

                        return userClient.createUser(newUser)
                                .map(jwtUtil::generateToken);
                    }
                });
    }

    private UserDto createUserDto(RegisterVM registerVM, String hashedPassword) {
        UserDto newUser = new UserDto();
        newUser.setEmail(registerVM.getEmail());
        newUser.setPasswordHash(hashedPassword);
        newUser.setFirstName(registerVM.getFirstName());
        newUser.setLastName(registerVM.getLastName());
        newUser.setActivated(true);
        newUser.setUserType("CUSTOMER");
        return newUser;
    }
}
