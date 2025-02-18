package com.example.bookhive.web.rest;

import com.example.bookhive.entity.Customer;
import com.example.bookhive.repository.CustomerRepository;
import com.example.bookhive.security.JwtService;
import com.example.bookhive.service.AuthenticationService;
import com.example.bookhive.service.dto.CustomerDto;
import com.example.bookhive.service.dto.LoginUserDto;
import com.example.bookhive.service.dto.RefreshTokenDto;
import com.example.bookhive.service.dto.RegisterUserDto;
import com.example.bookhive.service.responce.LoginResponse;
import com.example.bookhive.service.responce.RefreshTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationResource {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;


    public AuthenticationResource(
            AuthenticationService authenticationService,
            JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomerDto> register(@RequestBody RegisterUserDto registerUserDto) {
        CustomerDto registeredUser = authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        Customer authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder().token(jwtToken).expiresIn(jwtService.getExpirationTime()).build();
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {

        Customer refreshTokenUser = authenticationService.authenticateRefresh(refreshTokenDto);

        String rwtToken = jwtService.generateRefreshToken(refreshTokenUser);

        RefreshTokenResponse refreshTokenResponse = RefreshTokenResponse.builder().token(rwtToken).expiresIn(jwtService.getExpirationTimeRefresh()).build();

        return ResponseEntity.ok(refreshTokenResponse);

    }
}
