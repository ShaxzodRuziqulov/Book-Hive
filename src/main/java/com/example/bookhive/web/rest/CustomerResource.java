package com.example.bookhive.web.rest;

import com.example.bookhive.entity.Customer;
import com.example.bookhive.service.CustomerService;
import com.example.bookhive.service.dto.CustomerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
    private final CustomerService customerService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CustomerDto customerDto) throws URISyntaxException {
        CustomerDto result = customerService.create(customerDto);
        return ResponseEntity.created(new URI("/api/customer/create/" + result.getId())).body(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody CustomerDto customerDto, @PathVariable Long id) {
        if (customerDto.getId() != null && !customerDto.getId().equals(id)) {
            return ResponseEntity.badRequest().body("Invalid id");
        }
        CustomerDto result = customerService.update(customerDto);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        List<CustomerDto> findAllCustomers = customerService.findAllCustomers();
        return ResponseEntity.ok(findAllCustomers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        CustomerDto result = customerService.findById(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok().body("Customer deleted successfully: " + id);
    }

    @GetMapping("/me")
    public ResponseEntity<Customer> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Customer currentUser = (Customer) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }
}
