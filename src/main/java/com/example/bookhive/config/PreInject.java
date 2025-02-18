package com.example.bookhive.config;

import com.example.bookhive.entity.Customer;
import com.example.bookhive.entity.Role;
import com.example.bookhive.repository.CustomerRepository;
import com.example.bookhive.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class PreInject {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;

    public PreInject(
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            CustomerRepository customerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.customerRepository = customerRepository;
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    private Role createRole(String name, String description) {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        return role;
    }

    @PostConstruct
    public void setDefaultUsers() {
        if (roleRepository.count() == 0) {
            List<Role> roles = new ArrayList<>();
            roles.add(createRole("ROLE_ADMIN", "Admin"));
            roles.add(createRole("ROLE_USER", "User"));
            roleRepository.saveAll(roles);
        }

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        if (adminRole == null) {
            throw new IllegalStateException("ROLE_ADMIN topilmadi. Avval ro‘llarni yarating!");
        }

        if (customerRepository.count() == 0) {
            Customer customer = new Customer();
            customer.setName("name");
            customer.setEmail("admin@gmail.com");
            customer.setRole(adminRole);
            customer.setPassword(encodePassword("123"));
            customer.setTotalPurchasesAmount(1D);
            customerRepository.save(customer);
        }
    }
}
