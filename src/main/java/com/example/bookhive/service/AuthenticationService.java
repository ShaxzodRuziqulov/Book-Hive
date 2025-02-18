package com.example.bookhive.service;

import com.example.bookhive.entity.Customer;
import com.example.bookhive.repository.CustomerRepository;
import com.example.bookhive.repository.RoleRepository;
import com.example.bookhive.service.dto.CustomerDto;
import com.example.bookhive.service.dto.LoginUserDto;
import com.example.bookhive.service.dto.RefreshTokenDto;
import com.example.bookhive.service.dto.RegisterUserDto;
import com.example.bookhive.service.mapper.CustomerMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;


    public AuthenticationService(CustomerRepository customerRepository, CustomerMapper customerMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationManager authenticationManager) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
    }

    public CustomerDto signUp(RegisterUserDto input) {
        if (input.getEmail().isEmpty() || input.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email bo‘sh bo‘lmasligi kerak");
        }
        Customer customer = customerMapper.toUser(input);
        customer.setPassword(passwordEncoder.encode(input.getPassword()));
        if (input.getRoleId() == null) {
            customer.setRole(roleRepository.findByName("ROLE_USER"));
        }
        customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    public Customer authenticate(LoginUserDto input) {
        if (input.getEmail() == null || input.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email kiritilishi shart");
        }
        if (input.getPassword() == null || input.getPassword().isEmpty()) {
            throw new IllegalArgumentException(("Parol kiritilishi shart"));
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Email yoki parol noto'g'ri");
        }
        return customerRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Foydalanuvchi topilmadi " + input.getEmail()));
    }

    public Customer authenticateRefresh(RefreshTokenDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return customerRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

}
